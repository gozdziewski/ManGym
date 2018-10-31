package pl.edu.zut.wi.pgozdziewski.mangym;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ExerciseCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_creation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise_creation, menu);
        return true;
    }

    public void onConfirmation(View v)
    {
        GymDatabase db = new GymDatabase(this);

        EditText e = (EditText) findViewById(R.id.exCreateName);

        if (e.getText().length() == 0) {
            finish();
            return;
        }

        String name = e.getText().toString();

        db.exerciseCreate(name);

        Toast.makeText(this, "Utworzono nowe ćwiczenie " + name, Toast.LENGTH_LONG).show();

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
