package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeacherMainActivity extends AppCompatActivity {

    Button btnPupils;
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
        setContentView(R.layout.activity_teacher_main);

        btnPupils = findViewById(R.id.btnPupils);
        btnOptions = findViewById(R.id.btnOptions);
        btnLogout = findViewById(R.id.btn_logout);
        //Intent intentUser = getIntent();
        loggedInUser = data.getUsername();//intentUser.getStringExtra("username");
        userRole = data.getRole();//intentUser.getStringExtra("role");

        btnPupils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeacherMainActivity.this, TeacherListPupilsActivity.class);
                //intent.putExtra("username",loggedInUser);
                startActivity(intent);
            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this, OptionsActivity.class);
                //intent.putExtra("username",loggedInUser);
                //intent.putExtra("role",userRole);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.LogOut(TeacherMainActivity.this);
                Intent intent = new Intent(TeacherMainActivity.this, LoginActivity.class);
                startActivity(intent);
                TeacherMainActivity.this.finish();
            }
        });
    }
}
