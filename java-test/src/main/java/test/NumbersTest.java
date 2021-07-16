package test;

import java.math.BigDecimal;

public class NumbersTest {

    public static void main(String[] args) {
        double rtr = new BigDecimal("4.343733534535071E-7").doubleValue();
        double rtr1 = 0.000000000343733534535071;

        System.out.println(String.valueOf(rtr));
        System.out.println(String.valueOf(rtr1));

        System.out.println(rtr+"");
        System.out.println(rtr1+"");
    }

}
