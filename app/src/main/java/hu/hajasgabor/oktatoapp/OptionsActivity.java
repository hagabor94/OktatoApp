package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

public class OptionsActivity extends AppCompatActivity {

    EditText edt_oldPwd;
    EditText edt_newPwd;
    EditText edt_newPwd2;
    Button btn_sendPwd;
    String loggedInUser;
    String userRole;
    String newPwd;
    String newPwd2;
    String oldPwd;
    boolean successfulRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        btn_sendPwd = findViewById(R.id.btn_setPwd);
        edt_newPwd = findViewById(R.id.edt_NewPwd);
        edt_newPwd2 = findViewById(R.id.edt_NewPwd2);
        edt_oldPwd = findViewById(R.id.edt_OldPwd);
        Intent intentUser = getIntent();
       loggedInUser = intentUser.getStringExtra("username");
       userRole = intentUser.getStringExtra("role");

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
    }
}
