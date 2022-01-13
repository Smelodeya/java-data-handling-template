package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     *
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {

        return new BigDecimal(a).divide(new BigDecimal(b), range, RoundingMode.HALF_UP);

    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {

        int tempRange = 0;
        BigInteger j = BigInteger.valueOf(2);
        BigInteger primaryNumber = BigInteger.ZERO;
        BigInteger endCalculation;
        BigInteger i;

        while (tempRange <= range) {
            primaryNumber = j;
            endCalculation = BigInteger.valueOf((long) Math.sqrt(primaryNumber.doubleValue()));
            boolean isPrimary = true;
            i = BigInteger.valueOf(2);
            while (((i.compareTo(endCalculation)) <= 0) && (isPrimary)) {
                if (primaryNumber.remainder(i).equals(BigInteger.ZERO)) {
                    isPrimary = false;
                }
                i = i.add(BigInteger.ONE);
            }
            if (isPrimary) {
                tempRange++;
            }

            j = j.add(BigInteger.ONE);
        }
        return primaryNumber;
    }
}
