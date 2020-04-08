package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TeacherListPupilsActivity extends AppCompatActivity {

    HorizontalScrollView horScroll;
    ListView listView;
    List<PupilForTeacher> pupilsList = new ArrayList<>();
    Cursor pupils;
    String teacher;
    UsernameRoleTheme data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = Utility.GetUsernameRoleTheme(this);
        if(data.isDarktheme())
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);
        setContentView(R.layout.activity_teacher_list_pupils);

        horScroll = findViewById(R.id.horizontalscroll);
        listView = findViewById(R.id.ListViewPupilTeacher);

        //Intent getUsername = getIntent();
        teacher = data.getUsername(); //getUsername.getStringExtra("username");

        Context mContext = this;
        final DataAdapter mDbHelper = new DataAdapter(mContext);
        mDbHelper.createDatabase();
        mDbHelper.open();
        pupils = mDbHelper.getPupilsDataForTeachers(teacher);
        mDbHelper.close();

        do{
            pupilsList.add(new PupilForTeacher(pupils.getString(pupils.getColumnIndexOrThrow("name")),
                    pupils.getInt(pupils.getColumnIndexOrThrow("solved_tests")),
                    pupils.getFloat(pupils.getColumnIndexOrThrow("avg_points")),
                    pupils.getString(pupils.getColumnIndexOrThrow("parent_name"))));
        } while (pupils.moveToNext());

        PupilForTeacherListAdapter adapter = new PupilForTeacherListAdapter(pupilsList,this);
        listView.setAdapter(adapter);
    }
}
