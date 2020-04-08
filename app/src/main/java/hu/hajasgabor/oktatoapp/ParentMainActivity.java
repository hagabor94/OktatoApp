package hu.hajasgabor.oktatoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ParentMainActivity extends AppCompatActivity {

    Button btnChildren;
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
        setContentView(R.layout.activity_parent_main);

        btnChildren = findViewById(R.id.btnChildren);
        btnOptions = findViewById(R.id.btnOptions);
        btnLogout = findViewById(R.id.btn_logout);
        //Intent intentUser = getIntent();
        loggedInUser = data.getUsername();//intentUser.getStringExtra("username");
        userRole = data.getRole();//intentUser.getStringExtra("role");

        btnChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ParentMainActivity.this,ParentListChildrenActivity.class);
                //intent.putExtra("username",loggedInUser);
                startActivity(intent);
            }
        });
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentMainActivity.this, OptionsActivity.class);
                //intent.putExtra("username",loggedInUser);
                //intent.putExtra("role",userRole);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.LogOut(ParentMainActivity.this);
                Intent intent = new Intent(ParentMainActivity.this,LoginActivity.class);
                startActivity(intent);
                ParentMainActivity.this.finish();
            }
        });
    }
}
