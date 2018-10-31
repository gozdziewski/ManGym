package pl.edu.zut.wi.pgozdziewski.mangym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pl.edu.zut.wi.pgozdziewski.mangym.model.Exercise;
import pl.edu.zut.wi.pgozdziewski.mangym.model.WorkoutPlan;

public class WorkoutPlanAdapter extends ArrayAdapter<WorkoutPlan> {

    String days[] = {"Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"};

    public WorkoutPlanAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public WorkoutPlanAdapter(Context context, int resource, List<WorkoutPlan> items) {
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

        WorkoutPlan p = getItem(position);

        if (p != null) {
            TextView n = (TextView) v.findViewById(R.id.name);

            GymDatabase db = new GymDatabase(n.getContext());

            if (n != null) {
                n.setText(days[p.getDay()] + ": " + p.getExercise());
            }

        }

        return v;
    }

}
