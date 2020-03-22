package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class QuizResultActivity extends AppCompatActivity {

    TextView txtQuizResult;
    TextView txtTests;
    TextView txtAvgPoints;
    int points;
    Cursor mData;
    String username;
    int solvedTests;
    float avgPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        Intent intent2 = getIntent();
        points = intent2.getIntExtra("points", 0);
        username = intent2.getStringExtra("username");

        txtQuizResult = findViewById(R.id.txtQuizResult);
        txtAvgPoints = findViewById(R.id.txt_avgPoints);
        txtTests = findViewById(R.id.txt_tests);

        Context context = this;
        final DataAdapter mDbHelper = new DataAdapter(context);
        mDbHelper.createDatabase();
        mDbHelper.open();
        mDbHelper.UpdatePupilQuizResult(username, points);
        mData = mDbHelper.getOnePupilData(username);
        solvedTests = mData.getInt(mData.getColumnIndexOrThrow("solved_tests"));
        avgPoints = mData.getFloat(mData.getColumnIndexOrThrow("avg_points"));
        mDbHelper.close();

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        txtQuizResult.setText("Pontszám: " + points);
        txtTests.setText("Befejezett kvízek: " + solvedTests);
        txtAvgPoints.setText("Átlag pontszám: " + df.format(avgPoints));
    }
}
