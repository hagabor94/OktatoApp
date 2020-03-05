package hu.hajasgabor.oktatoapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static hu.hajasgabor.oktatoapp.QuestionsContract.SQL_CREATE_TABLE;
import static hu.hajasgabor.oktatoapp.QuestionsContract.SQL_DROP_TABLE;

public class QuestionsOpenHelper extends SQLiteOpenHelper {

    //ez akkor ha én akarom létrehozni
    // public static final String DB_NAME = "QuestionsDatabase.db";
    public static final String DB_NAME = "QuestionsDB.db";
    public static final int DB_VERSION = 1;
    private final File DB_FILE;
    private SQLiteDatabase mDatabase;
    private final Context mContext;

    public QuestionsOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        DB_FILE = context.getDatabasePath(DB_NAME);
        this.mContext = context;
    }


    public void createDataBase() throws IOException {
        if(!DB_FILE.exists()){
            this.getReadableDatabase();
            this.close();
            try{
                copyDataBase();
            } catch (IOException ioException){
                throw new Error("ErrorCopyingDatabase");
            }
        }
    }

    private void copyDataBase() throws IOException{
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_FILE);
        byte[] mbuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mbuffer)) > 0){
            mOutput.write(mbuffer,0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDatabase() throws SQLException{
        mDatabase = SQLiteDatabase.openDatabase(String.valueOf(DB_FILE), null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDatabase!= null;
    }

    @Override
    public synchronized  void close(){
        if(mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       //ez akkor ha én akarom létrehozni
        // db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // ez akkor ha én akarom létrehozni
        // db.execSQL(SQL_DROP_TABLE);
        // onCreate(db);
    }
}
