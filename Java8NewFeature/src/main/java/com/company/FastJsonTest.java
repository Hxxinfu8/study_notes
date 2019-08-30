package com.company;

import com.alibaba.fastjson.JSONObject;

public class FastJsonTest {
    public static void main(String[] args) {
        Task task = new Task(Status.OPEN, 1);

        System.out.println(JSONObject.toJSONString(task));
    }
}
