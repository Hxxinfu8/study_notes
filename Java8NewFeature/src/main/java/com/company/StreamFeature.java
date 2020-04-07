package com.company;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Steam之上的操作可分为中间操作和晚期操作。
 * 中间操作会返回一个新的steam——执行一个中间操作（例如filter）并不会执行实际的过滤操作，而是创建一个新的steam，并将原steam中符合条件的元素放入新创建的steam。
 * 晚期操作（例如forEach或者sum），会遍历steam并得出结果或者附带结果；在执行晚期操作之后，steam处理线已经处理完毕，就不能使用了。在几乎所有情况下，晚期操作都是立刻对steam进行遍历。
 */
public class StreamFeature {
    public static void main(String[] args) {
        Integer[] list = new Integer[]{1, 3, 4, 1, 4, 2, 5};
        System.out.println(Arrays.stream(list).filter(item -> item == 1).mapToInt(item -> item).sum());

        List<Task> tasks = Arrays.asList(new Task(Status.CLOSED, 10), new Task(Status.OPEN, 21), new Task(Status.OPEN, 12));

        System.out.println(tasks.parallelStream().filter(item -> item.getStatus() == Status.OPEN).mapToInt(Task::getPoints).max().getAsInt());

        final Integer total = tasks.parallelStream().mapToInt(Task::getPoints).sum();
        System.out.println(total);
        System.out.println(tasks.parallelStream().filter(item -> item.getStatus() == Status.OPEN).mapToInt(Task::getPoints).sum());
        System.out.println(tasks.stream().collect(Collectors.groupingBy(Task::getStatus)));
        System.out.println(tasks.stream().mapToInt(Task::getPoints).mapToDouble(item -> item/total).boxed().mapToLong(item -> (long)(item*100)).mapToObj(item -> item + "%").collect(Collectors.toList()));

        Map<String, Task> map = new HashMap<>();
        map.put("1", new Task(Status.CLOSED, 1));
        map.put("2", new Task(Status.CLOSED, 1));
        map.put("3", new Task(Status.CLOSED, 2));
        map.put("4", new Task(Status.CLOSED, 2));
        Map<Integer, List<Map.Entry<String, Task>>> result = map.entrySet().stream()
                .collect(Collectors.groupingBy(item -> item.getValue().getPoints()));
        System.out.println(result.toString());
        System.out.println(result.entrySet().stream().map(item -> item.getValue().stream().map(cc -> cc.getKey())).toString());
        Map<Integer, List<String>> okMap = new HashMap<>();
        result.forEach((key, value) -> {
            okMap.put(key, value.stream().map(item -> item.getKey()).collect(Collectors.toList()));
        });
        System.out.println(okMap.toString());
    }
}
