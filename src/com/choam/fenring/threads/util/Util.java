package com.choam.fenring.threads.util;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Util {

    public static final BigDecimal BigDecimal_FOUR = BigDecimal.valueOf(4);

    public static void printThreadInfo(Thread thread) {
        System.out.printf(
                "%s is %salive and in state %s%n",
                thread.getName(),
                (thread.isAlive() ? "" : "not "),
                thread.getState()
        );
    }

    public static BigDecimal computePie(int digits) {

        /*
         * pi/4 = 4*arctan(1/5)-arctan(1/239)
         */

        int scale = digits + 5;

        return BigDecimal_FOUR
                .multiply(arctan(5, scale, BigDecimal.ROUND_HALF_EVEN))
                .subtract(arctan(239, scale, BigDecimal.ROUND_HALF_EVEN))
                .multiply(BigDecimal_FOUR)
                .setScale(digits, BigDecimal.ROUND_HALF_UP)
                ;
    }

    public static BigDecimal arctan(int inverseX, int scale, int roundingMode) {

        /*
         * x = 1/inverseX
         * arctan(x) = x-(x^3)/3+(x^5)/5-(x^7)/7+(x^9)/9 ...
         */

        BigDecimal result, numer, term;

        BigDecimal x = BigDecimal.ONE.divide(BigDecimal.valueOf(inverseX), scale, roundingMode);
        BigDecimal xSquared = BigDecimal.ONE.divide(BigDecimal.valueOf(inverseX * inverseX), scale, roundingMode);

        result = BigDecimal.ZERO;
        int denom = 1;
        numer = x;

        do {
            term = numer.divide(BigDecimal.valueOf(denom), scale, roundingMode);

            if ((((denom - 1) / 2) % 2) == 0) {
                result = result.add(term);
            } else {
                result = result.subtract(term);
            }

            numer = numer.multiply(xSquared);
            denom += 2;

        } while (term.compareTo(BigDecimal.ZERO) != 0);

        return result;
    }

    public static double getAverageNumber(ArrayList<Number> numberList, boolean excludeExtremes) {

        if (!excludeExtremes) { return getAverageNumber(numberList); }

        Number min, max;

        ArrayList<Number> localNumberList = new ArrayList<>(numberList);

        if (localNumberList.size() <= 2) {
            return 0;
        } else {
            min = localNumberList.get(0);
            max = localNumberList.get(1);
        }

        for (Number number : localNumberList) {

            if (number instanceof Integer || number instanceof Long) {

                if ((long) number < (long) min) {
                    min = number;
                }
                if ((long) number > (long) max) {
                    max = number;
                }
            } else if ((number instanceof Float || number instanceof Double)) {

                if ((double) number < (double) min) {
                    min = number;
                }
                if ((double) number > (double) max) {
                    max = number;
                }
            } else {
                return 0;
            }
        }

        localNumberList.remove(min);
        localNumberList.remove(max);

        return getAverageNumber(localNumberList);
    }

    public static double getAverageNumber(ArrayList<Number> numberList) {

        if (numberList.size() <= 0) { return  0; }

        double sum = 0;
        double size = numberList.size() + 1;

        for (Number number : numberList) {
            if (number instanceof Integer || number instanceof Long) {
                sum += (long) number;
            } else if ((number instanceof Float || number instanceof Double)) {
                sum += (double) number;
            } else {
                return 0;
            }
        }

        return sum / size;
    }

    public static String getOrderedNumber(long number) {

        String orderedNumber;

        if (number <= 0) { return "Goth"; }

        String numberString = number + "";
        String lastDigit = numberString.substring(numberString.length() - 1);
        String lastTwoDigits = (number > 10) ? numberString.substring(numberString.length() - 2) : lastDigit;

        if (lastDigit.equals("1") && !lastTwoDigits.equals("11")) {
            orderedNumber = numberString + "st";
        } else if (lastDigit.equals("2") && !lastTwoDigits.equals("12")) {
            orderedNumber = numberString + "nd";
        } else if (lastDigit.equals("3") && !lastTwoDigits.equals("13")) {
            orderedNumber = numberString + "rd";
        } else {
            orderedNumber = numberString + "th";
        }

        return orderedNumber;
    }

    public static void outputException(Exception e) {
        System.out.println("Exception occured: " + e);
    }
}