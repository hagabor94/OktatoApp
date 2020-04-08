package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PupilMainActivity extends AppCompatActivity {

    Button btnStart;
    Button btnOptions;
    Button btnLogout;
    String loggedInUser;
    String userRole;
    UsernameRoleTheme data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = Utility.GetUsernameRoleTheme(this);
        if(data.isDarktheme())
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);
        setContentView(R.layout.activity_pupil_main);

        btnStart = findViewById(R.id.btnStart);
        btnOptions = findViewById(R.id.btnOptions);
        btnLogout = findViewById(R.id.btn_logout);
        //Intent intentUser = getIntent();
        loggedInUser = data.getUsername();//intentUser.getStringExtra("username");
        userRole = data.getRole();//intentUser.getStringExtra("role");

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PupilMainActivity.this, hu.hajasgabor.oktatoapp.QuizActivity.class);
                //intent.putExtra("username",loggedInUser);
                startActivity(intent);

            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PupilMainActivity.this,OptionsActivity.class);
                //intent.putExtra("username",loggedInUser);
                //intent.putExtra("role",userRole);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.LogOut(PupilMainActivity.this);
                Intent intent = new Intent(PupilMainActivity.this,LoginActivity.class);
                startActivity(intent);
                PupilMainActivity.this.finish();
            }
        });
    }
}
