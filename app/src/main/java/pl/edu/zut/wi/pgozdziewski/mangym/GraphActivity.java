package pl.edu.zut.wi.pgozdziewski.mangym;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pl.edu.zut.wi.pgozdziewski.mangym.model.Workout;

public class GraphActivity extends AppCompatActivity {

    List<Workout> workouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        workouts = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("EXERCISE");

            //tutaj zapytanie do db
            GymDatabase db = new GymDatabase(this);

            Cursor c = db.workoutSelectByExcerciseName(value);

            //jak cursor nie jest nullem i są wpisy to w pętli odczytujemy z bazy danych
            if (c != null) {
                if (c.moveToFirst()) {
                    workouts.clear();
                    do {
                        Workout workout = new Workout();
                        workout.setSeries(c.getInt(c.getColumnIndex(GymDatabase.WO_SRS)));
                        workout.setId(c.getInt(c.getColumnIndex(GymDatabase.WO_ID)));
                        workout.setRepeats(c.getInt(c.getColumnIndex(GymDatabase.WO_RPTS)));
                        workout.setExercise(c.getString(c.getColumnIndex(GymDatabase.WO_EXRC)));
                        workout.setWeight(c.getInt(c.getColumnIndex(GymDatabase.WO_SRS)));
                        workout.setTime(c.getInt(c.getColumnIndex(GymDatabase.WO_TIME)));

                        workouts.add(workout);
                    } while (c.moveToNext());
                }
            }

            DataPoint[] graphPoints = new DataPoint[workouts.size()];

            Collections.sort(workouts, new Comparator<Workout>() {
                @Override
                public int compare(Workout  workout1, Workout  workout2)
                {
                    return (workout1.getSeries() - workout2.getSeries());
                }
            });
            for(int i = 0; i < workouts.size(); ++i)
            {
                graphPoints[i] = new DataPoint(workouts.get(i).getSeries(), workouts.get(i).getRepeats());
            }

            GraphView graph = (GraphView) findViewById(R.id.graph);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphPoints);
            graph.addSeries(series);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
