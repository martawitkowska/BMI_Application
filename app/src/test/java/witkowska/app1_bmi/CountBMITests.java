package witkowska.app1_bmi;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017-03-16.
 */

public class CountBMITests {

    @Test
    public void masUnder0_isInvalid() {
        //GIVEN
        float mass = -1.0f;
        //WHEN
        ICountBMI countBMI = new CountBMIforKgM();
        //THEN
        assertFalse(countBMI.isValidMass(mass));
    }

    @Test
    public void heightUnder0_isInvalid() {
        //GIVEN
        float height = -0.1f;
        //WHEN
        ICountBMI countBMI = new CountBMIforKgM();
        //THEN
        assertFalse(countBMI.isValidHeight(height));
    }

    @Test
    public void personalMass_isValid() {
        //GIVEN
        float mass = 55f;
        //WHEN
        ICountBMI countBMI = new CountBMIforKgM();
        //THEN
        assertTrue(countBMI.isValidMass(mass));
    }

    @Test
    public void personalHeight_isValid() {
        //GIVEN
        float height = 1.7f;
        //WHEN
        ICountBMI countBMI = new CountBMIforKgM();
        //THEN
        assertTrue(countBMI.isValidHeight(height));
    }

    @Test
    public void personalMassLbInch_isValid() {
        //GIVEN
        float mass = 100f;
        //WHEN
        ICountBMI countBMI = new CountBMIforLbInch();
        //THEN
        assertTrue(countBMI.isValidHeight(mass));
    }

    @Test
    public void personalHeightLbInch_isValid() {
        //GIVEN
        float height = 72f;
        //WHEN
        ICountBMI countBMI = new CountBMIforLbInch();
        //THEN
        assertTrue(countBMI.isValidHeight(height));
    }

}
