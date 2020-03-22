package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PupilMainActivity extends AppCompatActivity {

    Button btnStart;
    Button btnOptions;
    String loggedInUser;
    String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pupil_main);

        btnStart = findViewById(R.id.btnStart);
        btnOptions = findViewById(R.id.btnOptions);
        Intent intentUser = getIntent();
        loggedInUser = intentUser.getStringExtra("username");
        userRole = intentUser.getStringExtra("role");

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PupilMainActivity.this, hu.hajasgabor.oktatoapp.QuizActivity.class);
                intent.putExtra("username",loggedInUser);
                startActivity(intent);

            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PupilMainActivity.this,OptionsActivity.class);
                intent.putExtra("username",loggedInUser);
                intent.putExtra("role",userRole);
                startActivity(intent);
            }
        });
    }
}
