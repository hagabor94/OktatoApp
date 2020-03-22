package hu.hajasgabor.oktatoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ParentMainActivity extends AppCompatActivity {

    Button btnChildren;
    Button btnOptions;
    String loggedInUser;
    String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pupil_main);

        btnChildren = findViewById(R.id.btnStart);
        btnOptions = findViewById(R.id.btnOptions);
        Intent intentUser = getIntent();
        loggedInUser = intentUser.getStringExtra("username");
        userRole = intentUser.getStringExtra("role");

        btnChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentMainActivity.this, OptionsActivity.class);
                intent.putExtra("username",loggedInUser);
                intent.putExtra("role",userRole);
                startActivity(intent);
            }
        });
    }
}
