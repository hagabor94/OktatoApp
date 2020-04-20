package hu.hajasgabor.oktatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MatchActivity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {
    TextView txt_objective;
    TextView txt_event1;
    TextView txt_event2;
    TextView txt_event3;
    TextView txt_event4;
    TextView txt_placeholder1;
    TextView txt_placeholder2;
    TextView txt_placeholder3;
    TextView txt_placeholder4;
    TextView txt_date1;
    TextView txt_date2;
    TextView txt_date3;
    TextView txt_date4;
    Button btn_check;
    UsernameRoleTheme data;
    Cursor mData;
    List<Integer> matchSequence = new ArrayList<>();
    List<Integer> currentMatchQuestions = new ArrayList<>();
    List<Integer> currentMatchAnswers = new ArrayList<>();
    int matchCounter = 0;
    int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = Utility.GetUsernameRoleTheme(this);
        if (data.isDarktheme())
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);
        setContentView(R.layout.activity_match);

        txt_objective = findViewById(R.id.txt_objective);
        txt_event1 = findViewById(R.id.txt_event1);
        txt_event2 = findViewById(R.id.txt_event2);
        txt_event3 = findViewById(R.id.txt_event3);
        txt_event4 = findViewById(R.id.txt_event4);
        txt_placeholder1 = findViewById(R.id.txt_date1_placeholder);
        txt_placeholder2 = findViewById(R.id.txt_date2_placeholder);
        txt_placeholder3 = findViewById(R.id.txt_date3_placeholder);
        txt_placeholder4 = findViewById(R.id.txt_date4_placeholder);
        txt_date1 = findViewById(R.id.txt_date1);
        txt_date2 = findViewById(R.id.txt_date2);
        txt_date3 = findViewById(R.id.txt_date3);
        txt_date4 = findViewById(R.id.txt_date4);
        btn_check = findViewById(R.id.btn_checkAnswer);

        txt_date1.setOnTouchListener(this);
        txt_date2.setOnTouchListener(this);
        txt_date3.setOnTouchListener(this);
        txt_date4.setOnTouchListener(this);
        txt_placeholder1.setOnDragListener(this);
        txt_placeholder2.setOnDragListener(this);
        txt_placeholder3.setOnDragListener(this);
        txt_placeholder4.setOnDragListener(this);

        Context mContext = this;
        final DataAdapter mDbHelper = new DataAdapter(mContext);
        mDbHelper.createDatabase();
        mDbHelper.open();
        mData = mDbHelper.getMatchData();
        mDbHelper.close();
        matchSequence = Utility.RandomMatchQuestions(mData.getCount());
        Log.d("MatchActivity", "matchSequence = " + matchSequence);

        fillActivity(mData);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_check.setVisibility(View.INVISIBLE);
                final TextView[] placeholders = {txt_placeholder1, txt_placeholder2, txt_placeholder3, txt_placeholder4};
                final TextView[] dates = {txt_date1, txt_date2, txt_date3, txt_date4};
                String date;
                for (int i = 0; i < 4; i++) {
                    date = placeholders[i].getText().toString();
                    mData.moveToPosition(currentMatchQuestions.get(i));
                    if (mData.getString(mData.getColumnIndexOrThrow("date")).equals(date)) {
                        points++;
                        placeholders[i].setBackgroundResource(R.color.transparentgreen);
                        placeholders[i].invalidate();
                    } else {
                        placeholders[i].setBackgroundResource(R.color.transparentred);
                        placeholders[i].invalidate();
                    }
                }

                new CountDownTimer(3500, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        btn_check.setVisibility(View.VISIBLE);
                        for (int i = 0; i < 4; i++) {
                            placeholders[i].setBackgroundResource(R.color.transparentwhite);
                            placeholders[i].setTag("EMPTY");
                            placeholders[i].setText("");
                            placeholders[i].invalidate();

                            dates[i].setVisibility(View.VISIBLE);
                        }

                        if (matchCounter < matchSequence.size())
                            fillActivity(mData);
                        else {
                            Intent intent = new Intent(MatchActivity.this, MatchResultActivity.class);
                            intent.putExtra("points",points);
                            startActivity(intent);
                            MatchActivity.this.finish();
                        }
                    }
                }.start();
            }
        });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
            ClipData data = ClipData.newPlainText("", "");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.startDragAndDrop(data, shadow, v, 0);
            } else {
                v.startDrag(data, shadow, v, 0);
            }
        }
        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {

        View view = (View) event.getLocalState();
        TextView dropped = (TextView) view;
        TextView dropTarget = (TextView) v;

        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                dropped.setVisibility(View.INVISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                if (dropTarget.getTag().toString().equals("EMPTY")) {
                    view.setVisibility(View.INVISIBLE);
                    dropTarget.setText(dropped.getText().toString());
                    dropTarget.setTag("DROPPED");
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                if (!event.getResult())
                    dropped.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        return true;
    }

    private void fillActivity(Cursor data) {
        setCurrentMatches();
        TextView[] events = {txt_event1, txt_event2, txt_event3, txt_event4};
        TextView[] dates = {txt_date1, txt_date2, txt_date3, txt_date4};
        for (int i = 0; i < 4; i++) {
            data.moveToPosition(currentMatchQuestions.get(i));
            events[i].setText(data.getString(data.getColumnIndexOrThrow("event")));
            data.moveToPosition(currentMatchAnswers.get(i));
            dates[i].setText(data.getString(data.getColumnIndexOrThrow("date")));
        }
    }

    //ezzel adok meg 4 feladatot amivel kitölthetek egy activityt
    private void setCurrentMatches() {
        int counter = matchCounter;
        int n = matchCounter + 4;
        int j = 0;
        if (matchCounter >= 4) {
            for (int i = counter; i < n; i++) {
                currentMatchQuestions.set(j, matchSequence.get(i));
                currentMatchAnswers.set(j, matchSequence.get(i));

                j++;
                matchCounter++;
            }
        } else {
            for (int i = counter; i < n; i++) {
                currentMatchQuestions.add(matchSequence.get(i));
                currentMatchAnswers.add(matchSequence.get(i));

                matchCounter++;
            }
        }
        Log.d("MatchActivity", "currentMatchQuestions " + currentMatchQuestions);
        Log.d("MatchActivity", "currentMatchAnswers " + currentMatchAnswers);
        Utility.RandomMatchAnswers(currentMatchAnswers);
        Log.d("MatchActivity", "currentMatchAnswers mixed " + currentMatchAnswers);
    }

}
