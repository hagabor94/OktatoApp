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

public class ParentListChildrenActivity extends AppCompatActivity {

    HorizontalScrollView horScroll;
    ListView listView;
    List<Pupil> pupils = new ArrayList<>();
    Cursor children;
    String parent;
    UsernameRoleTheme data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = Utility.GetUsernameRoleTheme(this);
        if(data.isDarktheme())
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);
        setContentView(R.layout.activity_parent_list_children);

        horScroll = findViewById(R.id.horizontalscroll);
        listView = findViewById(R.id.ListViewPupilParent);

        //Intent getUsername = getIntent();
        parent=data.getUsername();//getUsername.getStringExtra("username");

        Context mContext = this;
        final DataAdapter mDbHelper = new DataAdapter(mContext);
        mDbHelper.createDatabase();
        mDbHelper.open();
        children=mDbHelper.getPupilsDataForParents(parent);
        mDbHelper.close();

        do{
            pupils.add(new Pupil(children.getString(children.getColumnIndexOrThrow("name")),
                    children.getInt(children.getColumnIndexOrThrow("solved_tests")),
                    children.getFloat(children.getColumnIndexOrThrow("avg_points"))));
        } while (children.moveToNext());

        PupilListAdapter adapter = new PupilListAdapter(pupils,this);
        listView.setAdapter(adapter);
    }
}
