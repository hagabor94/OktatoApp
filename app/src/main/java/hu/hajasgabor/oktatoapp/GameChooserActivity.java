package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class GameChooserActivity extends AppCompatActivity {

    Button btn_start;
    RadioButton radio_quiz;
    RadioButton radio_match;
    UsernameRoleTheme data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = Utility.GetUsernameRoleTheme(this);
        if (data.isDarktheme())
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);
        setContentView(R.layout.activity_game_chooser);

        btn_start = findViewById(R.id.btn_start);
        radio_match= findViewById(R.id.radio_match);
        radio_quiz = findViewById(R.id.radio_quiz);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(radio_quiz.isChecked()){
                    intent = new Intent(GameChooserActivity.this,QuizActivity.class);
                } else {
                    intent = new Intent(GameChooserActivity.this,MatchActivity.class);
                }
                startActivity(intent);
                GameChooserActivity.this.finish();
            }
        });
    }
}
