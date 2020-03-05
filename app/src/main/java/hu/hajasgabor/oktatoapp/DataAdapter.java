package hu.hajasgabor.oktatoapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

public class DataAdapter {

    private final Context mContext;
    private SQLiteDatabase mDb;
    private QuestionsOpenHelper mDbHelper;

    public DataAdapter(Context context){
        this.mContext = context;
        mDbHelper = new QuestionsOpenHelper(mContext);
    }

    public DataAdapter createDatabase() throws SQLException{
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataAdapter open() throws SQLException{
        try {
            mDbHelper.openDatabase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch(SQLException mSQLException){
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public Cursor getData(){
        try {
            String sql = "SELECT * FROM questionsdata";
            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur != null){
                mCur.moveToNext();
            }
            return mCur;
        } catch(SQLException mSQLException){
            throw mSQLException;
        }
    }

}
