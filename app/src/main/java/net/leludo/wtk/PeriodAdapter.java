package net.leludo.wtk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ludovic on 13/01/2018.
 */

public class PeriodAdapter extends ArrayAdapter<Period> {

    private final Context context;
    private final List<Period> values;

    public PeriodAdapter(Context context, List<Period> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.duration_item, parent, false);
        TextView duration = (TextView) rowView.findViewById(R.id.duration);
        TextView startTime = (TextView) rowView.findViewById(R.id.startTime);
        TextView endTime = (TextView) rowView.findViewById(R.id.endTime);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        duration.setText(values.get(position).format());
        startTime.setText(values.get(position).formatedStartTime());
        endTime.setText(values.get(position).formatedEndTime());
        // change the icon for Windows and iPhone

        return rowView;
    }
}
