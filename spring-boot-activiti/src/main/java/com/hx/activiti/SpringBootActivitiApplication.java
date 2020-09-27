package com.hx.activiti;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Upoint0002
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SpringBootActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootActivitiApplication.class, args);
    }

}
