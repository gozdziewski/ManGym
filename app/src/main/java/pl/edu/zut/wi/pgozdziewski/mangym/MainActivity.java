package pl.edu.zut.wi.pgozdziewski.mangym;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onButtonClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.doWorkoutButton:
                startActivity(new Intent(this, DoWorkoutActivity.class));
                break;
            case R.id.planButton:
                startActivity(new Intent(this, WorkoutPlanActivity.class));
                break;
            case R.id.exercisesButton:
                startActivity(new Intent(this, ExercisesActivity.class));
                break;
            case R.id.historyButton:
                startActivity(new Intent(this, HistoryMainScreenActivity.class));
                break;
            case R.id.calculatorButton:
                startActivity(new Intent(this, CalculatorActivity.class));
                break;
        }
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
