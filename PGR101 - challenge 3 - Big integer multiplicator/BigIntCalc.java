import java.util.ArrayList;

/**
 * Created by Clément on 16/02/2016.
 * !!! The class does NOT check if the strings are uniquely composed of number characters.
 */
public class BigIntCalc {

    String number1, number2;
    String larger, smaller;

    /**
     * Default constructor.
     * Initialised with the second example from utfordring 3 to test
     * products with remainders.
     */
    public BigIntCalc() {
        this("2312", "2341");
    }

    public BigIntCalc(String num1, String num2) {
        this.number1 = num1;
        this.number2 = num2;
    }

    /**
     * Takes two string-format factors initialised in the constructor and
     * multiplies them.
     *
     * @return the product of both factors.
     */

    public String calculation() {

        // Defining which of the two numbers is biggest
        if (number1.length() < number2.length()) {
            larger = number2;
            smaller = number1;
        } else {
            larger = number1;
            smaller = number2;
        }

        // Split each factor's digits into arrays of individual characters
        char[] largerFactor = larger.toCharArray();
        char[] smallerFactor = smaller.toCharArray();

        //
        int largerExp = largerFactor.length; // 4
        int smallerExp = smallerFactor.length; // 3


        ArrayList<String> firstProduct = new ArrayList<String>();

        // total amount of coefficients of same grade
        int totalStepCount = largerExp + smallerExp - 1;

        int i = 0;
        while (i < totalStepCount) {

            int currentProduct = 0;

            int jStart = 0;
            int jEnd = i;

            // Reassign start and end of the loop when reaching the
            // x^0 extremes of either polynomial
            if (i >= smallerExp) {
                jStart = i - (smallerExp - 1);
            }
            if (i >= largerExp - 1) {
                jEnd = largerExp - 1;
            }

            for (int j = jStart; j <= jEnd; j++) {
                //
                int a = Integer.parseInt(String.valueOf(largerFactor[j]));
                int b = Integer.parseInt(String.valueOf(smallerFactor[i - j]));

                currentProduct += a * b;
            }

            // Save coefficients in growing order
            // (i.e. leading term of the polynomial product on last index)
            firstProduct.add(0, ("" + currentProduct));

            i++;
        }

        String remainder = "0";
        String sum = "";
        String finalProduct = "";

        for (String s : firstProduct) {

            if (remainder.equals("0")) {
                sum = s;
            } else {
                sum = addition(s, remainder);
            }
            finalProduct = sum.substring(sum.length() - 1) + finalProduct;

            if (sum.length() < 2) {
                remainder = "0";
            } else {
                remainder = sum.substring(0, sum.length() - 1);
            }
        }

        if (!remainder.equals("0")) {
            finalProduct = remainder + finalProduct;
        }

        System.out.println(finalProduct);
        System.out.println();
        return finalProduct;
    }


    /**
     * Add a string number with another
     */
    private String addition(String num1, String num2) {
        String biggerNum = num1;
        String smallerNum = num2;

        if (num1.length() < num2.length()) {
            biggerNum = num2;
            smallerNum = num1;
        }

        // Give an equal number of digits to both numbers
        while (smallerNum.length() < biggerNum.length()) {
            smallerNum = "0" + smallerNum;
        }

        char[] numArray1 = biggerNum.toCharArray();
        char[] numArray2 = smallerNum.toCharArray();
        String total = "";
        short rest = 0;

        for (int i = numArray1.length - 1; i >= 0; --i) {
            int a = numArray1[i] - '0';
            int b = numArray2[i] - '0';
            int sum = a + b + rest;

            total = "" + (sum % 10) + total;

            if (sum > 9) {
                rest = 1;
            } else {
                rest = 0;
            }
        }
        if (rest == 1) {
            total = "" + rest + total;
        }

        return total;
    }

}
