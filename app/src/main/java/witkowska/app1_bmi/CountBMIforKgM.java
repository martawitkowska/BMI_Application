package witkowska.app1_bmi;

import android.util.Log;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by Administrator on 2017-03-16.
 */

public class CountBMIforKgM extends MainActivity implements ICountBMI {

    static final float MIN_MASS = 10f;
    static final float MAX_MASS = 250f;
    static final float MIN_HEIGHT = 0.5f;
    static final float MAX_HEIGHT = 2.5f;

    @Override
    public boolean isValidMass(float mass) {
        return (MIN_MASS <= mass && mass <= MAX_MASS);
    }

    @Override
    public boolean isValidHeight(float height) {
        return (MIN_HEIGHT <= height && height <= MAX_HEIGHT);
    }

    @Override
    public float countBMI(float mass, float height) throws IllegalArgumentException {
        if (isValidMass(mass) && isValidHeight(height))
            return mass/(height*height);
        else
            throw new IllegalArgumentException();
    }

}
