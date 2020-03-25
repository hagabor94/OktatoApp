package hu.hajasgabor.oktatoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class PupilForTeacherListAdapter extends BaseAdapter {

    List<PupilForTeacher> pupils;
    Context context;

    public PupilForTeacherListAdapter(List<PupilForTeacher> pupils, Context context) {
        this.pupils = pupils;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup teacher) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_pupil_for_teacher, teacher, false);
        }

        TextView name = convertView.findViewById(R.id.txt_list_name);
        TextView solved = convertView.findViewById(R.id.txt_list_solved);
        TextView avg = convertView.findViewById(R.id.txt_list_avg);
        TextView parent = convertView.findViewById(R.id.txt_list_parent);

        PupilForTeacher pupil = pupils.get(position);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String avgString = String.valueOf(df.format(pupil.getAvgPoints()));

        name.setText(pupil.getName());
        solved.setText(String.valueOf(pupil.getSolvedTests()));
        avg.setText(avgString);
        parent.setText(pupil.getParent());

        return convertView;
    }
}
