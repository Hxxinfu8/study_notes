package com.company.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {
    public static void main(String[] args) {
        List<SortVO> list = new ArrayList<>();
        list.add(new SortVO(true));
        list.add(new SortVO(false));
        list.add(new SortVO(true));
        list.add(new SortVO(false));
        list.add(new SortVO(false));
        list.add(new SortVO(true));

        List<SortVO> result = list.stream().sorted(Comparator.comparing(SortVO::getStatus).reversed()).collect(Collectors.toList());
        System.out.println(result);
    }
}
