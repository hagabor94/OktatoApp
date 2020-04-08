package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText txtUsername;
    EditText txtPassword;
    Spinner loginSpinner;
    boolean successfulLogin;
    String mUsername;
    String mPassword;
    String role;
    String theme;
    UsernameRoleTheme data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ez a pár sor itt az új, lehet törölni kell mindenhol
        data = Utility.GetUsernameRoleTheme(this);
        if(data.isDarktheme())
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        txtUsername = findViewById(R.id.txt_username);
        txtPassword = findViewById(R.id.txt_password);
        loginSpinner = findViewById(R.id.loginSpinner);


        //theme = Utility.GetThemeName(this); ehelyett van data.isdarktheme
        ArrayAdapter<CharSequence> arrayAdapter;
        if(data.isDarktheme()){
            arrayAdapter = ArrayAdapter.createFromResource(this, R.array.loginChoices_array, R.layout.spinner_layout_dark);
            arrayAdapter.setDropDownViewResource(R.layout.spinner_item_layout_dark);
        }
        else{
            arrayAdapter = ArrayAdapter.createFromResource(this, R.array.loginChoices_array, R.layout.spinner_layout_light);
            arrayAdapter.setDropDownViewResource(R.layout.spinner_item_layout_light);
        }
        loginSpinner.setAdapter(arrayAdapter);

        /*ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.loginChoices_array, R.layout.spinner_layout_dark);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item_layout_dark);
        loginSpinner.setAdapter(arrayAdapter);*/

        /*ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.loginChoices_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loginSpinner.setAdapter(arrayAdapter);*/

        Context mContext = this;
        final DataAdapter mDbHelper = new DataAdapter(mContext);
        mDbHelper.createDatabase();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername = txtUsername.getText().toString();
                mPassword = txtPassword.getText().toString();
                mDbHelper.open();
                if (txtUsername.getText().toString().equals("") || txtPassword.getText().toString().equals(""))
                    Toast.makeText(LoginActivity.this, "Adjon meg felhasználónevet és jelszót!", Toast.LENGTH_SHORT).show();
                else {
                    try {
                        LoginSelected(mDbHelper, mUsername, mPassword);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void LoginSelected(DataAdapter mDbHelper, String username, String password) throws NoSuchAlgorithmException {
        Intent intent = new Intent();
        switch (loginSpinner.getSelectedItemPosition()) {
            case 0:
                successfulLogin = mDbHelper.LoginPupil(username, password);
                intent = new Intent(LoginActivity.this, hu.hajasgabor.oktatoapp.PupilMainActivity.class);
                role = "pupil";
                //intent.putExtra("role", "pupil");
                break;
            case 1:
                successfulLogin = mDbHelper.LoginParent(username, password);
                intent = new Intent(LoginActivity.this, hu.hajasgabor.oktatoapp.ParentMainActivity.class);
                role="parent";
                //intent.putExtra("role", "parent");
                break;
            case 2:
                successfulLogin = mDbHelper.LoginTeacher(username, password);
                intent = new Intent(LoginActivity.this, hu.hajasgabor.oktatoapp.TeacherMainActivity.class);
                role="teacher";
                //intent.putExtra("role", "teacher");
                break;
        }
        //intent.putExtra("username", username);
        Utility.SaveUser(this,username,role);
        mDbHelper.close();
        if (successfulLogin)
            startActivity(intent);
        else
            Toast.makeText(LoginActivity.this, "Hibás felhasználónév vagy jelszó!", Toast.LENGTH_SHORT).show();
    }
}
