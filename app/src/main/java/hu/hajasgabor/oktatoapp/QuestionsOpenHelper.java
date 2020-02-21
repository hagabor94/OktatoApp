package hu.hajasgabor.oktatoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static hu.hajasgabor.oktatoapp.QuestionsContract.SQL_CREATE_TABLE;
import static hu.hajasgabor.oktatoapp.QuestionsContract.SQL_DROP_TABLE;

public class QuestionsOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "QuestionsDatabase.db";
    public static final int DB_VERSION = 1;

    public QuestionsOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }
}
