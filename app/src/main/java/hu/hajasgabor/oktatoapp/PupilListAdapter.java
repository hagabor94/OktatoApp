package hu.hajasgabor.oktatoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class PupilListAdapter extends BaseAdapter {

    private List<Pupil> pupils;
    private Context context;
    private String theme;

    public PupilListAdapter(List<Pupil> pupils, Context context) {
        this.pupils = pupils;
        this.context = context;
        theme = Utility.GetThemeName(context);
    }

    @Override
    public int getCount() {
        return pupils.size();
    }

    @Override
    public Object getItem(int position) {
        return pupils.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(theme.equals("DarkTheme"))
                convertView = inflater.inflate(R.layout.list_item_pupil_dark, parent, false);
            else
                convertView = inflater.inflate(R.layout.list_item_pupil_light, parent, false);
        }

        TextView name = convertView.findViewById(R.id.txt_list_name);
        TextView solved = convertView.findViewById(R.id.txt_list_solved);
        TextView avg = convertView.findViewById(R.id.txt_list_avg);

        Pupil pupil = pupils.get(position);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String avgString = String.valueOf(df.format(pupil.getAvgPoints()));

        name.setText(pupil.getName());
        solved.setText(String.valueOf(pupil.getSolvedTests()));
        //avg.setText(String.valueOf(pupil.getAvgPoints()));
        avg.setText(avgString);

        return convertView;
    }
}
