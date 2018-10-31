package pl.edu.zut.wi.pgozdziewski.mangym;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.zut.wi.pgozdziewski.mangym.model.Workout;

public class HistoryActivity extends AppCompatActivity {

    ListView history;
    HistoryAdapter adapter;
    List<Workout> workouts;
    String exerciseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        workouts = new ArrayList<>();
        history = (ListView) findViewById(R.id.historyListView);
        adapter = new HistoryAdapter(this, R.layout.listitem_workout, workouts);
        history.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            exerciseName = extras.getString("EXERCISE");
            refreshList();
        }
    }

    void refreshList()
    {
        //tutaj zapytanie do db
        GymDatabase db = new GymDatabase(this);

        Cursor c = db.workoutSelectByExcerciseName(exerciseName);

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
                    Log.i("History", "kolejny workaut");
                } while (c.moveToNext());
            }
        }

        int sumOfCalories = 0;
        for (int i = 0; i < workouts.size(); ++i)
        {
            sumOfCalories += (workouts.get(i).getTime() / 10);
        }

        TextView s = (TextView) findViewById(R.id.textViewCalories);
        if (s != null)
        {
            s.setText("Spalone kalorie: " + sumOfCalories);
        }

        //klasycznie odświeżamy widok listy
        adapter.notifyDataSetChanged();
    }

    public void onButtonClick(View v) {
        Intent intent = new Intent(this, GraphActivity.class);
        intent.putExtra("EXERCISE", exerciseName);
        startActivity(intent);
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
