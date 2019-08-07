package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalTest {
    public static void main(String[] args) {
        System.out.println(new BigDecimal(8000).divide(new BigDecimal(1024 * 1024)).longValue());

        // 四舍五入
        System.out.println(new BigDecimal(1024*1024 *1.4).divide(new BigDecimal(1024 * 1024), RoundingMode.HALF_UP).longValue());

        System.out.println(new BigDecimal(8000).divide(new BigDecimal(1024 * 1024), RoundingMode.UP).longValue());
        System.out.println(new BigDecimal(8000).divide(new BigDecimal(1024 * 1024), RoundingMode.DOWN).longValue());
    }
}
