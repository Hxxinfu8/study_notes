package com.company;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 时间新特性
 */
public class TimeFeature {
    public static void main(String[] args) {
        Clock clock = Clock.systemUTC();
        System.out.println(clock.instant());  // 当前时间
        System.out.println(clock.millis());  // 当前毫秒数
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.getDayOfMonth());
        LocalDate now = LocalDate.now(clock);
        System.out.println(now.format(DateTimeFormatter.BASIC_ISO_DATE));

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.toLocalDate());


        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
        System.out.println(zonedDateTime.toLocalDateTime());

        LocalDateTime before = LocalDateTime.of(2019, 4, 10, 10, 10, 10);
        LocalDateTime after = LocalDateTime.of(2019, 5, 8, 9, 9, 9);

        System.out.println(Duration.between(before, after).getSeconds());
        System.out.println(Duration.between(before, after).toDays());
    }
}
