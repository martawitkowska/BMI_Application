package witkowska.app1_bmi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.*;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.massEditText) EditText massEditText;
    @BindView(R.id.heightEditText) EditText heightEditText;
    @BindView(R.id.countedBMIEditText) EditText countedBMI;
    @BindView(R.id.bmiInfoTextView) TextView info;
    @BindView(R.id.BMITextView) TextView BMIText;
    @BindView(R.id.button) Button button;
    @BindView(R.id.massUnitTextView) TextView massUnit;
    @BindView(R.id.heightUnitTextView) TextView heightUnit;

    @BindColor(R.color.gray) int gray;
    @BindColor(R.color.gray_dark) int gray_dark;
    @BindColor(R.color.green) int green;
    @BindColor(R.color.green_dark) int green_dark;
    @BindColor(R.color.orange) int orange;
    @BindColor(R.color.orange_dark) int orange_dark;
    @BindColor(R.color.red) int red;
    @BindColor(R.color.red_dark) int red_dark;

    Units option;
    public enum Units {
        KG_M, KG_CM, LB_INCH;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("BMI counter");
        option = Units.KG_M;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.unit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.kg_m_option:
                option = Units.KG_M;
                massUnit.setText(getString(R.string.kg));
                heightUnit.setText(getString(R.string.m));
                break;
            case R.id.kg_cm_option:
                option = Units.KG_CM;
                massUnit.setText(getString(R.string.kg));
                heightUnit.setText(getString(R.string.cm));
                break;
            case R.id.lb_inch_option:
                option = Units.LB_INCH;
                massUnit.setText(getString(R.string.lb));
                heightUnit.setText(getString(R.string.inch));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @OnClick (R.id.button)
    public void ButtonClick(View view) {
        try {
            float mass = Float.valueOf(massEditText.getText().toString());
            float height = Float.valueOf(heightEditText.getText().toString());

            float bmi = dependingOnUnitBMI(mass, height);

            String formatedBMI = bmi < 100 ? String.format("%.01f", bmi) : String.valueOf((int)bmi);
            countedBMI.setText(formatedBMI);
            colorChange(bmi, countedBMI);

        } catch (IllegalArgumentException ex) {
            Toast toast = Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 170);
            toast.show();
        }
    }

    public float dependingOnUnitBMI(float mass, float height) {
        float bmi = 0;
        switch (option) {
            case KG_M:
                CountBMIforKgM kg_m = new CountBMIforKgM();
                bmi = kg_m.countBMI(mass, height);
                break;
            case KG_CM:
                CountBMIforKgM kg_cm = new CountBMIforKgM();
                bmi = kg_cm.countBMI(mass, (height/100));
                break;
            case LB_INCH:
                CountBMIforLbInch lb_inch = new CountBMIforLbInch();
                bmi = lb_inch.countBMI(mass, height);
                break;
        }
        return bmi;
    }

    public void colorChange(float bmi, TextView countedBMI) {
        if (bmi < 18.5f) {
            countedBMI.setBackgroundColor(gray);
            BMIText.setBackgroundColor(gray_dark);
            info.setText(getString(R.string.under));
        }
        else if (bmi < 25f) {
            countedBMI.setBackgroundColor(green);
            BMIText.setBackgroundColor(green_dark);
            info.setText(getString(R.string.normal));
        }
        else if (bmi < 30f) {
            countedBMI.setBackgroundColor(orange);
            BMIText.setBackgroundColor(orange_dark);
            info.setText(getString(R.string.over));
        }
        else {
            countedBMI.setBackgroundColor(red);
            BMIText.setBackgroundColor(red_dark);
            info.setText(getString(R.string.obese));
        }

    }

}
