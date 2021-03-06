package pl.edu.zut.wi.pgozdziewski.mangym;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.zut.wi.pgozdziewski.mangym.model.Exercise;

public class ExercisesActivity extends AppCompatActivity {

    ListView exercisesListView;
    List<Exercise> exerciseList;
    ExercisesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        exercisesListView = (ListView) findViewById(R.id.exercisesListView);

        exerciseList = new ArrayList<>();

        adapter = new ExercisesAdapter(this, R.layout.listitem_simple, exerciseList);

        exercisesListView.setAdapter(adapter);

        //jak się przytrzyma wpis to startuje menu kontekstowe
        exercisesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    return false;
                }

                // Start the CAB using the ActionMode.Callback defined above
                mActionMode = startSupportActionMode(mActionModeCallback);
                mActionMode.setTag(position); //trzeba wiedzieć którą pozycję user przytrzymał
                view.setSelected(true);
                return true;
            }
        });

    }

    void refreshList()
    {
        //tutaj zapytanie do db
        GymDatabase db = new GymDatabase(this);

        Cursor c = db.exerciseSelectAll();

        //jak cursor nie jest nullem i są wpisy to w pętli odczytujemy z bazy danych
        if (c != null) {
            if (c.moveToFirst()) {
                exerciseList.clear();
                do {
                    int id = c.getInt(c.getColumnIndex(GymDatabase.EX_ID));
                    String name = c.getString(c.getColumnIndex(GymDatabase.EX_NAME));
                    exerciseList.add(new Exercise(id, name));
                } while (c.moveToNext());
            }
        }

        //klasycznie odświeżamy widok listy
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercises, menu);
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
        if (id == R.id.action_create) {
            startActivity(new Intent(this, ExerciseCreationActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //zmienna na actionmode czyli tryb kontekstowy, jest wszystko w poradniku androida
    private ActionMode mActionMode;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.plan_context_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_remove:
                    removeExerciseFromDatabase((int) mActionMode.getTag()); // jak ktoś chce usunąć to spradzamy który wpis był przytrzymany i usuwamy go ze świata
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    //usuwamy event z bazy - easy
    private void removeExerciseFromDatabase(int position) {
        //instancja bazy danych i usunięcie wpisu
        GymDatabase db = new GymDatabase(this);
        db.exerciseRemove(exerciseList.get(position));

        //usunięcie z listy wyśwetlania
        exerciseList.remove(position);
        //odświeżenie listy wyswietlanej
        adapter.notifyDataSetChanged();
    }
}
