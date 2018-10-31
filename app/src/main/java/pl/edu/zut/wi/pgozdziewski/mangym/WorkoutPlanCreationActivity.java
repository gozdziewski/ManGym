package pl.edu.zut.wi.pgozdziewski.mangym;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pl.edu.zut.wi.pgozdziewski.mangym.model.Exercise;

public class WorkoutPlanCreationActivity extends AppCompatActivity {

    private int selectedDay = 1;
    private String selectedWorkout= "";
    List<CharSequence> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_plan_creation);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDay = 0;
            }
        });


        exercises = new ArrayList<>();

        GymDatabase db = new GymDatabase(this);
        Cursor c = db.exerciseSelectAll();
        if (c != null) {
            if (c.moveToFirst()) {
                exercises.clear();
                do {
                    String name = c.getString(c.getColumnIndex(GymDatabase.EX_NAME));
                    exercises.add(name);
                } while (c.moveToNext());
            }
            else {
                exercises.add("Odpoczynek");
            }
        }
        else {
            exercises.add("Odpoczynek");
        }

        Spinner exSpinner = (Spinner) findViewById(R.id.exSpinner);
        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, exercises);
        exSpinner.setAdapter(adapter2);
        exSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedWorkout = exercises.get(position).toString();
                Log.i("Wybrane ćwiczenie: ", exercises.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedWorkout = exercises.get(0).toString();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout_plan_creation, menu);
        return true;
    }

    public void onConfirmation(View v) {
        GymDatabase db = new GymDatabase(this);

        db.workoutPlanCreate(selectedDay, selectedWorkout);

        Log.i("Dodawanie: ", selectedDay + ": " + selectedWorkout);

        finish();
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
