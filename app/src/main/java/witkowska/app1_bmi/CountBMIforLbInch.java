package witkowska.app1_bmi;

/**
 * Created by Administrator on 2017-03-26.
 */

public class CountBMIforLbInch extends MainActivity implements ICountBMI {

    //1 inch = 2,5 cm
    //1 lb = 0,45 kg

    static final float MIN_MASS = 20f;
    static final float MAX_MASS = 500f;
    static final float MIN_HEIGHT = 20f;
    static final float MAX_HEIGHT = 100f;

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
            return (mass*703)/(height*height);
        else
            throw new IllegalArgumentException(getString(R.string.error_message));
    }


}
