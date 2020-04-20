package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MatchResultActivity extends AppCompatActivity {

    TextView txt_matchResult;
    UsernameRoleTheme data;
    int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = Utility.GetUsernameRoleTheme(this);
        if(data.isDarktheme())
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);
        setContentView(R.layout.activity_match_result);

        Intent intent = getIntent();
        points = intent.getIntExtra("points",-1);

        txt_matchResult = findViewById(R.id.txt_matchResult);
        txt_matchResult.setText("Pontszám: "+points);

    }
}
