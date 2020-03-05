package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class QuizResultActivity extends AppCompatActivity {

    TextView txtQuizResult;
    int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        Intent intent2 = getIntent();
        points = intent2.getIntExtra("points",-1);
        txtQuizResult = findViewById(R.id.txtQuizResult);
        txtQuizResult.setText("Pontszám: "+points);
    }
}
