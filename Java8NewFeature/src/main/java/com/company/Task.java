package com.company;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Task implements Serializable {
    private Status status;
    private Integer points;
}
