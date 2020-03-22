package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        txtUsername = findViewById(R.id.txt_username);
        txtPassword = findViewById(R.id.txt_password);
        loginSpinner = findViewById(R.id.loginSpinner);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.loginChoices_array, R.layout.spinner_layout);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        loginSpinner.setAdapter(arrayAdapter);

        Context mContext = this;
        final DataAdapter mDbHelper = new DataAdapter(mContext);
        mDbHelper.createDatabase();

        loginSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                intent.putExtra("role", "pupil");
                break;
            case 1:
                successfulLogin = mDbHelper.LoginParent(username, password);
                intent = new Intent(LoginActivity.this, hu.hajasgabor.oktatoapp.ParentMainActivity.class);
                intent.putExtra("role", "parent");
                break;
            case 2:
                successfulLogin = mDbHelper.LoginTeacher(username, password);
                intent = new Intent(LoginActivity.this, hu.hajasgabor.oktatoapp.TeacherMainActivity.class);
                intent.putExtra("role", "teacher");
                break;
        }
        intent.putExtra("username", username);
        mDbHelper.close();
        if (successfulLogin)
            startActivity(intent);
        else
            Toast.makeText(LoginActivity.this, "Hibás felhasználónév vagy jelszó!", Toast.LENGTH_SHORT).show();
    }
}
