package pl.edu.zut.wi.pgozdziewski.mangym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pl.edu.zut.wi.pgozdziewski.mangym.model.Exercise;


public class HistoryMainScreenAdapter extends ArrayAdapter<Exercise> {

    public HistoryMainScreenAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public HistoryMainScreenAdapter(Context context, int resource, List<Exercise> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.listitem_simple, null);
        }

        Exercise p = getItem(position);

        if (p != null) {
            TextView e = (TextView) v.findViewById(R.id.name);
            e.setText(p.getName());
        }

        return v;
    }
}
