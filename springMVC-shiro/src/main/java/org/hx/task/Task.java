package org.hx.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Upoint0002
 */
@Component
public class Task {
    @Scheduled(cron = "30 * * * * ? ")
    public void test() {
        System.out.println("好好学习，天天向上");
    }
}
