package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

public class OptionsActivity extends AppCompatActivity {

    EditText edt_oldPwd;
    EditText edt_newPwd;
    EditText edt_newPwd2;
    Button btn_sendPwd;
    Button btn_saveQuit;
    Switch switch_theme;
    String loggedInUser;
    String userRole;
    String newPwd;
    String newPwd2;
    String oldPwd;
    boolean successfulRefresh = false;
    UsernameRoleTheme data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = Utility.GetUsernameRoleTheme(this);
        if(data.isDarktheme())
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);
        setContentView(R.layout.activity_options);

        btn_sendPwd = findViewById(R.id.btn_setPwd);
        btn_saveQuit = findViewById(R.id.btn_saveQuit);
        edt_newPwd = findViewById(R.id.edt_NewPwd);
        edt_newPwd2 = findViewById(R.id.edt_NewPwd2);
        edt_oldPwd = findViewById(R.id.edt_OldPwd);
        switch_theme = findViewById(R.id.switch_theme);
        //Intent intentUser = getIntent();
        loggedInUser = data.getUsername(); //intentUser.getStringExtra("username");
        userRole = data.getRole(); //intentUser.getStringExtra("role");
        switch_theme.setChecked(data.isDarktheme());

        btn_sendPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPwd = edt_oldPwd.getText().toString();
                newPwd = edt_newPwd.getText().toString();
                newPwd2 = edt_newPwd2.getText().toString();

                if(oldPwd.equals("") || newPwd.equals("") || newPwd2.equals(""))
                    Toast.makeText(OptionsActivity.this, "Ne maradjon üres mező!", Toast.LENGTH_SHORT).show();
                else {
                    if (!newPwd.equals(newPwd2))
                        Toast.makeText(OptionsActivity.this, "Az új jelszavak nem egyeznek!", Toast.LENGTH_SHORT).show();
                    else {
                        Context mContext = OptionsActivity.this;
                        DataAdapter mDbHelper = new DataAdapter(mContext);
                        mDbHelper.open();
                        try {
                            switch (userRole) {
                                case "pupil":
                                    successfulRefresh = mDbHelper.UpdatePupilPassword(loggedInUser, oldPwd, newPwd);
                                    break;
                                case "parent":
                                    successfulRefresh = mDbHelper.UpdateParentPassword(loggedInUser, oldPwd, newPwd);
                                    break;
                                case "teacher":
                                    successfulRefresh = mDbHelper.UpdateTeacherPassword(loggedInUser, oldPwd, newPwd);
                                    break;
                            }
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        if (successfulRefresh)
                            Toast.makeText(OptionsActivity.this, "Jelszó megváltoztatva!", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(OptionsActivity.this, "A régi jelszó hibás!", Toast.LENGTH_SHORT).show();
                        }
                        edt_oldPwd.getText().clear();
                        edt_newPwd.getText().clear();
                        edt_newPwd2.getText().clear();
                        mDbHelper.close();
                    }
                }
            }
        });

        switch_theme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   setTheme(R.style.DarkTheme);
                    Toast.makeText(OptionsActivity.this, "Sötét téma beállítva!", Toast.LENGTH_SHORT).show();
                }
                else{
                    setTheme(R.style.LightTheme);
                    Toast.makeText(OptionsActivity.this, "Világos téma beállítva!", Toast.LENGTH_SHORT).show();
                }
                //saveTheme();
                //recreate(); Sounds good, doesn't work
            }
        });

        btn_saveQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTheme();
                Intent intent;
                switch (userRole) {
                    case "pupil":
                        intent=new Intent(OptionsActivity.this, PupilMainActivity.class);
                        break;
                    case "parent":
                        intent=new Intent(OptionsActivity.this, ParentMainActivity.class);
                        break;
                    case "teacher":
                        intent=new Intent(OptionsActivity.this, TeacherMainActivity.class);
                        break;
                    default:
                        intent=new Intent(OptionsActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                OptionsActivity.this.finish();
            }
        });
    }

    private void saveTheme(){
        SharedPreferences sharedPref = getSharedPreferences("hu.hajasgabor.oktatoapp",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("dark_theme",switch_theme.isChecked());
        editor.apply();
    }
}
