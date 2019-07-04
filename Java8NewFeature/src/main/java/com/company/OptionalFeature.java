package com.company;

import java.util.Optional;

/**
 *  空值处理
 */
public class OptionalFeature {

    public static void main(String[] args) {
	// write your code here
        Optional<String> optional = Optional.ofNullable(null); //参数可为空
        Optional<String> optional1 = Optional.of("111");  // 参数不可为空，（代码运行时错误）
        Optional<String> optional2 = Optional.empty();
        System.out.println(optional2.isPresent());  // 为空返回false
        System.out.println(optional1.get());  // 获取值， toString()错误
        System.out.println(optional.orElseGet(() -> "null"));  // 持有为null时返回默认值， 需要lambda表达式传入
        System.out.println(optional.orElse("null"));
        System.out.println(optional.map(s -> "new" + s).orElse("null"));
    }
}
