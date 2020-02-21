package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    TextView txtQuestion;
    TextView txtHint;
    Button btnAnswer1;
    Button btnAnswer2;
    Button btnAnswer3;
    Button btnAnswer4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtQuestion = findViewById(R.id.txtQuestion);
        txtHint = findViewById(R.id.txtHint);
        btnAnswer1 = findViewById(R.id.btnAnswer1);
        btnAnswer2 = findViewById(R.id.btnAnswer2);
        btnAnswer3 = findViewById(R.id.btnAnswer3);
        btnAnswer4 = findViewById(R.id.btnAnswer4);

        QuestionsOpenHelper questionsOpenHelper = new QuestionsOpenHelper(this);
        questionsOpenHelper.getWritableDatabase();

        SQLiteDatabase db = questionsOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_QUESTION, "Első kérdés");
        values.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_ANSWER1, "Válasz 1");
        values.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_ANSWER2, "Válasz 2");
        values.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_ANSWER3, "Válasz 3");
        values.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_ANSWERCORRECT, "Jó válasz");
        values.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_HINT, "Ez a jó válasz");

        long id = db.insert(QuestionsContract.QuestionsEntry.TABLE_NAME,null,values);
        Log.d("QuizActivity","Id: "+String.valueOf(id));

        values.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_QUESTION, "Második kérdés");
        values.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_ANSWER1, "Válasz 1");
        values.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_ANSWER2, "Válasz 2");
        values.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_ANSWER3, "Válasz 3");
        values.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_ANSWERCORRECT, "Jó válasz");
        values.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_HINT, "Ez a jó válasz");

        id = db.insert(QuestionsContract.QuestionsEntry.TABLE_NAME,null,values);
        Log.d("QuizActivity","Id: "+String.valueOf(id));

        questionsOpenHelper.close();
    }
}
