public class Main {

    public static void main(String[] args) {

        String bignum1 = "9";
        String bignum2 = "2";

        String expectedResult = "121932631137021795226185032733866788594511507391563633592367611644557" +
                "885992987901082152001356500521260478584238530711635101356484634964180" +
                "575979271268602347185735406186461057765434857491222360920590123609205" +
                "801112635258986434993786160646167367779295611949397448712086533622923" +
                "332237463801111263526900";



        BigIntCalc calc = new BigIntCalc(bignum1, bignum2);

        String actualResult = calc.calculation();

        if(actualResult.equals(expectedResult)) System.out.println("VICTORY!!!");

        System.out.println("Result length: " + actualResult.length());
    }
}
