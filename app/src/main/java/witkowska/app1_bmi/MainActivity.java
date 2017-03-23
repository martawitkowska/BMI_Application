package witkowska.app1_bmi;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("BMI counter");

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.kg_m_option:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.kg_cm_option:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.lb_inch_option:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public void ButtonClick (View view) {
        try {
            EditText massEditText = (EditText) findViewById(R.id.massEditText);
            EditText heightEditText = (EditText) findViewById(R.id.heightEditText);

            float mass = Float.valueOf(massEditText.getText().toString());
            float height = Float.valueOf(heightEditText.getText().toString());

            CountBMIforKgM bmi_object = new CountBMIforKgM();
            float bmi = bmi_object.countBMI(mass, height);

            TextView countedBMI = (TextView) findViewById(R.id.countedBMIEditText);
            TextView BMIText = (TextView) findViewById(R.id.BMITextView);
            String formatedBMI = bmi < 100 ? String.format("%.01f", bmi) : String.valueOf((int)bmi);
            countedBMI.setText(formatedBMI);

            countedBMI.setTextColor(Color.parseColor("#000000"));
            BMIText.setTextColor(Color.parseColor("#000000"));

            if (bmi < 18.5f) {
                countedBMI.setBackgroundColor(Color.parseColor("#C0C0C0"));
                BMIText.setBackgroundColor(Color.parseColor("#A6A6A6"));
                }
            else if (bmi < 25f) {
                countedBMI.setBackgroundColor(Color.parseColor("#CCE89F"));
                BMIText.setBackgroundColor(Color.parseColor("#AAD763"));
                }
            else if (bmi < 30f) {
                countedBMI.setBackgroundColor(Color.parseColor("#FAF7A1"));
                BMIText.setBackgroundColor(Color.parseColor("#EDE86F"));
                }
            else if (bmi < 40f) {
                countedBMI.setBackgroundColor(Color.parseColor("#F5C98D"));
                BMIText.setBackgroundColor(Color.parseColor("#F0AE42"));
                }
            else {
                countedBMI.setBackgroundColor(Color.parseColor("#EE8C75"));
                BMIText.setBackgroundColor(Color.parseColor("#E26142"));
            }

        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            TextView countedBMI = (TextView) findViewById(R.id.countedBMIEditText);
            TextView BMIText = (TextView) findViewById(R.id.BMITextView);

            countedBMI.setText("X");
            countedBMI.setTextColor(Color.parseColor("#FFFFFF"));
            BMIText.setTextColor(Color.parseColor("#FFFFFF"));
            countedBMI.setBackgroundColor(Color.parseColor("#3F51B5"));
            BMIText.setBackgroundColor(Color.parseColor("#303F9F"));
        }
    }

}
