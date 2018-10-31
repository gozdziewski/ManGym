package pl.edu.zut.wi.pgozdziewski.mangym;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import pl.edu.zut.wi.pgozdziewski.mangym.model.Workout;
import pl.edu.zut.wi.pgozdziewski.mangym.model.WorkoutPlan;

public class DoWorkoutActivity extends AppCompatActivity {

    List<WorkoutPlan> workoutPlans;

    Workout workout;
    private List<CharSequence> exercises;
    ArrayAdapter<CharSequence> adapter;
    boolean isTimerRunning = false;
    int time = 0;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_workout);

        workoutPlans = new ArrayList<>();
        workout = new Workout();
        workout.setSeries(0);
        workout.setRepeats(0);
        workout.setWeight(0);

        exercises = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, exercises);

        fulfillListForSpinner();

        Spinner exSpinner = (Spinner) findViewById(R.id.availableExercises);

        exSpinner.setAdapter(adapter);
        exSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                workout.setExercise(exercises.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                workout.setExercise("Odpoczynek");
            }
        });

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (isTimerRunning) {
                    time += 1; //increase every sec
                    mHandler.obtainMessage(1).sendToTarget();
                }
            }
        }, 0, 1000);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        timer.cancel();
        timer.purge();
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            TextView text = (TextView) findViewById(R.id.timeLabel);
            text.setText(formatIntoHHMMSS(time)); //this is the textview
        }
    };

    public void onConfirmWorkout(View v) {
        TextView s = (TextView) findViewById(R.id.series);
        TextView r = (TextView) findViewById(R.id.repeats);
        TextView w = (TextView) findViewById(R.id.weight);

        if (s.getText().toString().equals("") || r.getText().toString().equals("") || w.getText().toString().equals(""))
        {

            Toast.makeText(DoWorkoutActivity.this, "Nie podałeś danych", Toast.LENGTH_SHORT).show();
        }
        else
        {

            workout.setSeries(Integer.parseInt(s.getText().toString()));
            workout.setRepeats(Integer.parseInt(r.getText().toString()));
            workout.setWeight(Integer.parseInt(w.getText().toString()));
            workout.setTime(time);
            workout.setDone(true);
            Log.d("MOJE", "read time=" + time);

            GymDatabase db = new GymDatabase(this);
            db.createWorkout(workout);

            finish();
        }

    }

    public void onStartStop(View v) {
        isTimerRunning ^= true;
        if (isTimerRunning)
        {
            Button button = (Button) findViewById(R.id.buttonStartStop);
            button.setText("STOP");
        }
        else
        {
            Button button = (Button) findViewById(R.id.buttonStartStop);
            button.setText("START");
        }
    }

    String formatIntoHHMMSS(int time)
    {
        int sec = time % 60;
        int min = time / 60;

        return String.format("%02d:%02d", min, sec);
    }

    void fulfillListForSpinner()
    {
        //tutaj zapytanie do db
        GymDatabase db = new GymDatabase(this);

        int today = Calendar.getInstance(Locale.getDefault()).get(Calendar.DAY_OF_WEEK);
        today -= 2;
        if (today == -1) today = 6;

        Log.d("Today", String.valueOf(today));

        Cursor c = db.workoutPlanSelectForDay(today);

        //jak cursor nie jest nullem i są wpisy to w pętli odczytujemy z bazy danych
        if (c != null) {
            if (c.moveToFirst()) {
                workoutPlans.clear();
                exercises.clear();
                do {
                    int id = c.getInt(c.getColumnIndex(GymDatabase.WP_ID));
                    int day = c.getInt(c.getColumnIndex(GymDatabase.WP_DAY));
                    String workout = c.getString(c.getColumnIndex(GymDatabase.WP_EXRC));

                    workoutPlans.add(new WorkoutPlan(id, day, workout));

                    exercises.add(workout);

                } while (c.moveToNext());
            }
            else
            {
                exercises.add("Odpoczynek");
            }
        }
        else
        {
            exercises.add("Odpoczynek");
        }

        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_do_workout, menu);
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
