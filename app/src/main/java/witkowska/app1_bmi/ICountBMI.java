package witkowska.app1_bmi;

/**
 * Created by Administrator on 2017-03-16.
 */

public interface ICountBMI {

    boolean isValidMass(float mass);
    boolean isValidHeight (float height);
    float countBMI (float mas, float height);

}