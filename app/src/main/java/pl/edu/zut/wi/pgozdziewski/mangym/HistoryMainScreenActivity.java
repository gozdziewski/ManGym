package pl.edu.zut.wi.pgozdziewski.mangym;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.zut.wi.pgozdziewski.mangym.model.Exercise;

public class HistoryMainScreenActivity extends AppCompatActivity {

    ListView history;
    HistoryMainScreenAdapter adapter;
    List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historymainscreen);

        exercises = new ArrayList<>();
        history = (ListView) findViewById(R.id.historyListView);
        adapter = new HistoryMainScreenAdapter(this, R.layout.listitem_workout, exercises);
        history.setAdapter(adapter);

        history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendIntent(position);
            }
        });

        refreshList();
    }

    void sendIntent(int position)
    {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("EXERCISE", exercises.get(position).getName());
        startActivity(intent);
    }

    void refreshList()
    {
        //tutaj zapytanie do db
        GymDatabase db = new GymDatabase(this);

        Cursor c = db.exerciseSelectAll();

        //jak cursor nie jest nullem i są wpisy to w pętli odczytujemy z bazy danych
        if (c != null) {
            if (c.moveToFirst()) {
                exercises.clear();
                do {
                    int id = c.getInt(c.getColumnIndex(GymDatabase.EX_ID));
                    String name = c.getString(c.getColumnIndex(GymDatabase.EX_NAME));
                    exercises.add(new Exercise(id, name));
                } while (c.moveToNext());
            }
        }

        //klasycznie odświeżamy widok listy
        adapter.notifyDataSetChanged();
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
