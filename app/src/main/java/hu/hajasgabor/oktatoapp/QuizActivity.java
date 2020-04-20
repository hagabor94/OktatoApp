package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    List<Integer> answers = new ArrayList<>();
    List<Integer> questionsSequence = new ArrayList<>();
    int currentQuestion;
    int questionCounter = 0;
    int points = 0;
    String username;
    UsernameRoleTheme uNRT;

    private void fillActivity(Cursor data, int questionId) {
        data.moveToPosition(questionId);
        answers = Utility.RandomQuizAnswers();

        txtQuestion.setText(data.getString(1));
        btnAnswer1.setText(data.getString(answers.get(0)));
        btnAnswer2.setText(data.getString(answers.get(1)));
        btnAnswer3.setText(data.getString(answers.get(2)));
        btnAnswer4.setText(data.getString(answers.get(3)));
        txtHint.setText(data.getString(6));

        btnAnswer1.setEnabled(true);
        btnAnswer2.setEnabled(true);
        btnAnswer3.setEnabled(true);
        btnAnswer4.setEnabled(true);
        txtHint.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        correct = false;
        firstTry = true;
    }

    private void checkAnswer(Button button, Cursor data) {
        if (button.getText().equals(data.getString(5))) {
            if (firstTry)
                points++;
            if (currentQuestion == questionsSequence.get(questionsSequence.size() - 1)) {
                Intent intent = new Intent(QuizActivity.this, QuizResultActivity.class);
                intent.putExtra("points", points);
                startActivity(intent);
                QuizActivity.this.finish();
            } else {
                questionCounter++;
                currentQuestion = questionsSequence.get(questionCounter);
            }
            correct = true;
            btnNext.setVisibility(View.VISIBLE);
        } else {
            firstTry = false;
        }
        button.setEnabled(false);
        txtHint.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uNRT = Utility.GetUsernameRoleTheme(this);
        if(uNRT.isDarktheme())
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);
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

        //Intent getUsername = getIntent();
        username = uNRT.getUsername(); //getUsername.getStringExtra("username");

        Context mContext = this;
        final DataAdapter mDbHelper = new DataAdapter(mContext);
        mDbHelper.createDatabase();
        mDbHelper.open();
        mData = mDbHelper.getQuestionsData();
        mDbHelper.close();
        questionsSequence = Utility.RandomQuizQuestions(mData.getCount());
        currentQuestion = questionsSequence.get(questionCounter);
        fillActivity(mData, currentQuestion);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAnswer1:
                checkAnswer(btnAnswer1, mData);
                break;
            case R.id.btnAnswer2:
                checkAnswer(btnAnswer2, mData);
                break;
            case R.id.btnAnswer3:
                checkAnswer(btnAnswer3, mData);
                break;
            case R.id.btnAnswer4:
                checkAnswer(btnAnswer4, mData);
                break;
            case R.id.btnNext:
                if (correct)
                    fillActivity(mData, currentQuestion);
        }
    }
}
