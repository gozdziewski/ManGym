package pl.edu.zut.wi.pgozdziewski.mangym;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pl.edu.zut.wi.pgozdziewski.mangym.model.Workout;


public class HistoryAdapter extends ArrayAdapter<Workout> {

    public HistoryAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public HistoryAdapter(Context context, int resource, List<Workout> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.listitem_workout, null);
        }

        Workout p = getItem(position);

        if (p != null) {
            TextView s = (TextView) v.findViewById(R.id.srs);
            TextView r = (TextView) v.findViewById(R.id.rpts);
            TextView w = (TextView) v.findViewById(R.id.wght);
            TextView t = (TextView) v.findViewById(R.id.time);
            TextView c = (TextView) v.findViewById(R.id.calories);

            if (s != null) {
                s.setText(String.valueOf(p.getSeries()));
            }
            if (r != null) {
                r.setText(String.valueOf(p.getRepeats()));
            }
            if (w != null) {
                w.setText(String.valueOf(p.getWeight()));
            }
            Log.d("MOJE", "read time=" + p.getTime());
            if (t != null) {
                t.setText(formatIntoHHMMSS(p.getTime()));
            }
            // Wzor na kalorie
            if (c != null) {
                c.setText(String.valueOf(p.getTime() / 10));
            }
        }

        return v;
    }

    String formatIntoHHMMSS(int time)
    {
        int sec = time % 60;
        int min = time / 60;

        return String.format("%02d:%02d", min, sec);
    }

}
