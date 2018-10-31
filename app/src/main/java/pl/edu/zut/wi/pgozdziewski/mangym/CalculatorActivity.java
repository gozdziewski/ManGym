package pl.edu.zut.wi.pgozdziewski.mangym;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        final Button button_calc = (Button) findViewById(R.id.button_calc);
        final EditText height = (EditText) findViewById(R.id.height);
        final EditText weight = (EditText) findViewById(R.id.weight);
        final TextView calc_result = (TextView) findViewById(R.id.calc_result);
        final TextView interval = (TextView) findViewById(R.id.interval);

        button_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double ht;
                double wt;
                double c_t;
                String intr = "";

                if (height.getText().toString().equals("") || weight.getText().toString().equals("")) {

                    Toast.makeText(CalculatorActivity.this, "Nie podałeś danych", Toast.LENGTH_SHORT).show();

                } else {

                    ht = Double.parseDouble(height.getText().toString());
                    wt = Double.parseDouble(weight.getText().toString());

                    ht=ht/100;
                    c_t = wt/(ht * ht);
                    calc_result.setText(String.valueOf(c_t));

                    if (c_t < 18.5) {
                        intr = "Masz niedowage";
                    } else if (c_t > 18.5 && c_t < 24.9) {
                        intr = "waga w normnie";
                    } else if (c_t > 24.9 && c_t < 29.9) {
                        intr = "Nadwaga";
                    } else if (c_t > 29.9 && c_t < 34.9) {
                        intr = "I stopień otyłości";
                    } else if (c_t > 34.9 && c_t < 39.9) {
                        intr = "II stopień otyłości";
                    } else if (c_t > 39.9) {
                        intr = "III stopień otyłości";
                    }

                    interval.setText(intr);

                }
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_calculator, menu);
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
