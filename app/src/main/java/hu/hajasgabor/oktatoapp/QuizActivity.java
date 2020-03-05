package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtQuestion;
    TextView txtHint;
    Button btnAnswer1;
    Button btnAnswer2;
    Button btnAnswer3;
    Button btnAnswer4;
    Button btnNext;
    boolean correct = false;
    boolean firstTry = true;
    Cursor mData;
    Randomizer rnd = new Randomizer();
    List<Integer> answers = new ArrayList<Integer>();
    List<Integer> questionsSequence = new ArrayList<>();
    int currentQuestion;
    int questionCounter = 0;
    int points = 0;

    private void fillActivity(Cursor data, int questionId){
        data.moveToPosition(questionId);
        answers = rnd.RandomQuizAnswers();

        txtQuestion.setText(data.getString(1));
        btnAnswer1.setText(data.getString(answers.get(0)));
        btnAnswer2.setText(data.getString(answers.get(1)));
        btnAnswer3.setText(data.getString(answers.get(2)));
        btnAnswer4.setText(data.getString(answers.get(3)));  //EZ MÉG NINCS TESZTELVE
        txtHint.setText(data.getString(6));

        btnAnswer1.setVisibility(View.VISIBLE);
        btnAnswer2.setVisibility(View.VISIBLE);
        btnAnswer3.setVisibility(View.VISIBLE);
        btnAnswer4.setVisibility(View.VISIBLE);
        txtHint.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        correct = false;
        firstTry = true;
    }

    private void checkAnswer(Button button, Cursor data){
        if(button.getText().equals(data.getString(5))){
            if( firstTry)
                points++;
            if (currentQuestion == questionsSequence.get(questionsSequence.size()-1)) {
                Intent intent = new Intent(QuizActivity.this, QuizResultActivity.class);
                intent.putExtra("points", points);
                startActivity(intent);
                QuizActivity.this.finish();
            }
            else{
                questionCounter++;
                currentQuestion = questionsSequence.get(questionCounter);
            }
            correct = true;
            btnNext.setVisibility(View.VISIBLE);
        }
        else{
            firstTry = false;
            button.setVisibility(View.INVISIBLE);
        }
        txtHint.setVisibility(View.VISIBLE);
    }

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
        btnNext = findViewById(R.id.btnNext);
        btnAnswer1.setOnClickListener(this);
        btnAnswer2.setOnClickListener(this);
        btnAnswer3.setOnClickListener(this);
        btnAnswer4.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        Context mContext = this;
        DataAdapter mDbHelper = new DataAdapter(mContext);
        mDbHelper.createDatabase();
        mDbHelper.open();
        mData = mDbHelper.getData();
        mDbHelper.close();
        questionsSequence = rnd.RandomQuizQuestions(mData.getCount());
        Toast.makeText(this, questionsSequence.toString(), Toast.LENGTH_SHORT).show();
        currentQuestion = questionsSequence.get(questionCounter);
        fillActivity(mData, currentQuestion);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAnswer1:
                checkAnswer(btnAnswer1,mData);
                break;
            case R.id.btnAnswer2:
                checkAnswer(btnAnswer2,mData);
                break;
            case R.id.btnAnswer3:
                checkAnswer(btnAnswer3,mData);
                break;
            case R.id.btnAnswer4:
                checkAnswer(btnAnswer4,mData);
                break;
            case R.id.btnNext:
                if (correct)
                    fillActivity(mData, currentQuestion);
        }
    }
}
