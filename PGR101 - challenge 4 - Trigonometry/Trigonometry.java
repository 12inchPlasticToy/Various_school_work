import javax.swing.*;
import java.util.Locale;

class Utfordring4 {

    public static void main(String[] args) {
	// write your code here
        calculate();
        //System.out.println(arctan(0.66666));
    }

    /**
     * Convert an angle from degrees to radians
     * @param degrees   an angle
     * @return  the same angle in radians
     */
    private static double radians (double degrees){
        return degrees * Math.PI / 180;
    }
    private static double degrees (double radians){
        return radians * 180 / Math.PI;
    }

    /**
     * Return the angle in degrees of tan(x)
     */
    private static double arctan(double xtan){
        double angle = 0;
        int precision = 100;
        double exp, numerator;
        for(int i = 0; i < precision; i++){
            exp = 2 * i + 1;
            numerator = Math.pow(-1,i);
            angle += numerator / exp * Math.pow(xtan,exp);
        }
        return degrees(angle);
    }

    /**
     * Return the sine of an angle
     * @param angleRad  the angle in radians
     * @return  the sine of the angle
     */
    private static double sin(double angleRad){
        double sine = 0;
        int precision = 100;
        double exp, numerator, denominator;
        for(int i = 0; i < precision; i++){
            exp = 2 * i + 1;
            numerator = power(-1,i);
            denominator = factorial(exp);

            sine += numerator / denominator * power(angleRad,exp);
        }
        return sine;
    }

    /**
     * Return the cosine of an angle
     * @param angleRad  the angle in radians
     * @return  the cosine of the angle
     */
    private static double cos(double angleRad){
        double cosine = 0;
        int precision = 100;
        double exp, numerator, denominator;
        for(int i = 0; i < precision; i++){
            exp = 2 * i;
            numerator = power(-1,i);
            denominator = factorial(exp);

            cosine += numerator / denominator * power(angleRad,exp);
        }
        return cosine;
    }

    /**
     * Return the single factorial of an integer
     * @param num   an integer
     * @return  the factorial of the input integer
     */
    private static double factorial(double num){
        double result = 1;
        for(int i = 1; i <= num; i++)result *= i;
        return result;
    }

    /**
     * Return the power of a number according to an integer exponent.
     *
     * @param num   the number to exponentiate
     * @param exp   the exponent
     * @return  num^exp
     */
    private static double power(double num, double exp){
        if(num == 0) return 0;
        if(exp == 0) return 1;
        if(exp == 1) return num;

        double pow = num;
        for(int i = 1; i < exp; i++) pow *= num;
        return pow;
    }

    /**
     * Produce an input dialog for an angle in degrees,
     * then show consecutive dialogs with the sine
     * then the cosine of that angle.
     */
    private static void calculate(){
        String input = JOptionPane.showInputDialog("Angle:\n");
        double angle, sine, cosine;
        try {
            angle = Double.parseDouble(input);
            sine = sin(radians(angle));
            cosine = cos(radians(angle));

            String message = String.format(Locale.FRANCE,"sin(%.1f) = %.4f",angle,sine);
            JOptionPane.showMessageDialog(null,message);
            message = String.format(Locale.FRANCE,"cos(%.1f) = %.4f",angle,cosine);
            JOptionPane.showMessageDialog(null,message);

        } catch (Exception exc){
            JOptionPane.showMessageDialog(null,"Error:\n" + exc);
        }
    }

}
