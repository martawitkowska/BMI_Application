package witkowska.app1_bmi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.*;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    @BindView(R.id.my_toolbar) Toolbar toolbar;

    @BindColor(R.color.colorPrimary) int primary;
    @BindColor(R.color.colorPrimaryDark) int primary_dark;
    @BindColor(R.color.gray) int gray;
    @BindColor(R.color.gray_dark) int gray_dark;
    @BindColor(R.color.green) int green;
    @BindColor(R.color.green_dark) int green_dark;
    @BindColor(R.color.orange) int orange;
    @BindColor(R.color.orange_dark) int orange_dark;
    @BindColor(R.color.red) int red;
    @BindColor(R.color.red_dark) int red_dark;

    static final int KG_M = 0;
    static final int KG_CM = 1;
    static final int LB_INCH = 2;
    boolean valid_data;
    float bmi;
    int option;
    boolean screen_rotated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.toolbar));

        if (savedInstanceState != null) {
            massEditText.setText(savedInstanceState.getString("massText"));
            heightEditText.setText(savedInstanceState.getString("heightText"));
            massEditText.setHint(savedInstanceState.getString("massHint"));
            heightEditText.setHint(savedInstanceState.getString("heightHint"));
            countedBMI.setText(savedInstanceState.getString("countedBMIText"));
            info.setText(savedInstanceState.getString("infoText"));
            massUnit.setText(savedInstanceState.getString("massUnit"));
            heightUnit.setText(savedInstanceState.getString("heightUnit"));
            BMIText.setBackgroundColor(savedInstanceState.getInt("color_dark"));
            countedBMI.setBackgroundColor(savedInstanceState.getInt("color"));
            valid_data = savedInstanceState.getBoolean("valid");
            option = savedInstanceState.getInt("option");
            screen_rotated = savedInstanceState.getBoolean("counter");
            bmi = savedInstanceState.getFloat("bmi");
        } else {
            option = KG_M;
            valid_data = false;
            bmi = 0;
        }
    }

    @OnClick (R.id.save_button)
    public void buttonSaveOnClick(View view) {
        if (valid_data) {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putFloat("mass", Float.valueOf(massEditText.getText().toString()));
            editor.putFloat("height", Float.valueOf(heightEditText.getText().toString()));
            editor.putInt("option", option);
            editor.putString("massUnit", massUnit.getText().toString());
            editor.putString("heightUnit", heightUnit.getText().toString());
            editor.putString("massHint", massEditText.getHint().toString());
            editor.putString("heightHint", heightEditText.getHint().toString());
            editor.commit();
            Toast toast = Toast.makeText(this, getString(R.string.save_valid_message), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 250);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, getString(R.string.save_error_message), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 250);
            toast.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences savedData = this.getPreferences(Context.MODE_PRIVATE);
        float mass_temp = savedData.getFloat("mass",0);
        float height_temp = savedData.getFloat("height",0);
        if (!screen_rotated && mass_temp > 0 && height_temp > 0) {
            massEditText.setText(String.valueOf(mass_temp));
            heightEditText.setText(String.valueOf(height_temp));
            massUnit.setText(savedData.getString("massUnit",getString(R.string.kg)));
            heightUnit.setText(savedData.getString("heightUnit",getString(R.string.m)));
            massEditText.setHint(savedData.getString("massHint",""));
            heightEditText.setHint(savedData.getString("heightHint",""));
            option = savedData.getInt("option",0);
            countValidBMI(mass_temp, height_temp);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("massText", massEditText.getText().toString());
        outState.putString("heightText", heightEditText.getText().toString());
        outState.putString("massHint", massEditText.getHint().toString());
        outState.putString("heightHint", heightEditText.getHint().toString());
        outState.putString("countedBMIText", countedBMI.getText().toString());
        outState.putString("infoText", info.getText().toString());
        outState.putInt("color", ((ColorDrawable)countedBMI.getBackground()).getColor());
        outState.putInt("color_dark", ((ColorDrawable)BMIText.getBackground()).getColor());
        outState.putBoolean("valid", valid_data);
        outState.putInt("option", option);
        outState.putBoolean("counter", screen_rotated);
        outState.putFloat("bmi", bmi);
        outState.putString("massUnit", massUnit.getText().toString());
        outState.putString("heightUnit", heightUnit.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        screen_rotated = true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setContentView(R.layout.activity_main);
        super.onConfigurationChanged(newConfig);
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
                option = KG_M;
                massUnit.setText(getString(R.string.kg));
                massEditText.setHint(getString(R.string.kg_hint));
                heightUnit.setText(getString(R.string.m));
                heightEditText.setHint(getString(R.string.m_hint));
                break;
            case R.id.kg_cm_option:
                option = KG_CM;
                massUnit.setText(getString(R.string.kg));
                massEditText.setHint(getString(R.string.kg_hint));
                heightUnit.setText(getString(R.string.cm));
                heightEditText.setHint(getString(R.string.cm_hint));
                break;
            case R.id.lb_inch_option:
                option = LB_INCH;
                massUnit.setText(getString(R.string.lb));
                massEditText.setHint(getString(R.string.lb_hint));
                heightUnit.setText(getString(R.string.inch));
                heightEditText.setHint(getString(R.string.inch_hint));
                break;

            case R.id.share:
                if (valid_data) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String formatedBMI = bmi < 100 ? String.format("%.01f", bmi) : String.valueOf((int)bmi);
                    String shareBodyText = getString(R.string.share_message_1) + " " + formatedBMI + " " + getString(R.string.share_message_2);
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_title));
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                    startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_option)));
                } else {
                    Toast toast = Toast.makeText(this, getString(R.string.share_error_message), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 250);
                    toast.show();
                }
                break;

            case R.id.author:
                startActivity(new Intent(getApplicationContext(), AuthorActivity.class));
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @OnClick (R.id.button)
    public void ButtonClick(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        try {
            float mass = Float.valueOf(massEditText.getText().toString());
            float height = Float.valueOf(heightEditText.getText().toString());
            countValidBMI(mass, height);

        } catch (IllegalArgumentException ex) {
            Toast toast = Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 290);
            toast.show();

            countedBMI.setBackgroundColor(primary);
            BMIText.setBackgroundColor(primary_dark);
            countedBMI.setText("-");
            info.setText("");
            valid_data = false;
        }
    }

    public void countValidBMI(float mass, float height) {
        bmi = dependingOnUnitBMI(mass, height);
        String formatedBMI = bmi < 100 ? String.format("%.01f", bmi) : String.valueOf((int)bmi);

        countedBMI.setText(formatedBMI);
        colorChange();
        valid_data = true;
    }

    public float dependingOnUnitBMI(float mass, float height) throws IllegalArgumentException {
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

    public void colorChange() {
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
