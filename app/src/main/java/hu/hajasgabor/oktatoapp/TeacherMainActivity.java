package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeacherMainActivity extends AppCompatActivity {

    Button btnPupils;
    Button btnOptions;
    String loggedInUser;
    String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        btnPupils = findViewById(R.id.btnPupils);
        btnOptions = findViewById(R.id.btnOptions);
        Intent intentUser = getIntent();
        loggedInUser = intentUser.getStringExtra("username");
        userRole = intentUser.getStringExtra("role");

        btnPupils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this, OptionsActivity.class);
                intent.putExtra("username",loggedInUser);
                intent.putExtra("role",userRole);
                startActivity(intent);
            }
        });
    }
}
