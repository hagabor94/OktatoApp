package hu.hajasgabor.oktatoapp;

import android.provider.BaseColumns;

public class QuestionsContract {

    private QuestionsContract(){ }

    public static class QuestionsEntry implements BaseColumns{
        public static final String TABLE_NAME = "questionsdata";
        public static final String COLUMN_NAME_QUESTION = "question";
        public static final String COLUMN_NAME_ANSWER1 =  "answer1";
        public static final String COLUMN_NAME_ANSWER2 = "answer2";
        public static final String COLUMN_NAME_ANSWER3 = "answer3";
        public static final String COLUMN_NAME_ANSWERCORRECT = "answercorrect";
        public static final String COLUMN_NAME_HINT = "answerhint";
    }

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + QuestionsEntry.TABLE_NAME + " (" +
                    QuestionsEntry._ID + " INTEGER PRIMARY KEY," +
                    QuestionsEntry.COLUMN_NAME_QUESTION + " TEXT," +
                    QuestionsEntry.COLUMN_NAME_ANSWER1 + " TEXT," +
                    QuestionsEntry.COLUMN_NAME_ANSWER2 + " TEXT," +
                    QuestionsEntry.COLUMN_NAME_ANSWER3 + " TEXT," +
                    QuestionsEntry.COLUMN_NAME_ANSWERCORRECT + " TEXT," +
                    QuestionsEntry.COLUMN_NAME_HINT + " TEXT)";

    public static final String SQL_DROP_TABLE =
        "DROP TABLE IF EXISTS " + QuestionsEntry.TABLE_NAME;
}
