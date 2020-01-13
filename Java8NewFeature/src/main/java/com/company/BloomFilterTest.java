package com.company;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author Upoint0002
 */
public class BloomFilterTest {
    public static void main(String[] args) {
        int total = 100000;
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), total);

        for(int i = 0; i < total; i ++) {
            filter.put(i);
        }

        for (int i = 0; i < total; i ++) {
            if (!filter.mightContain(i)) {
                System.out.println("未命中");
            }
        }

        int count = 0;
        for (int i = total ; i < total << 3; i ++) {
            if (filter.mightContain(total)) {
                count ++;
            }
        }
        System.out.println("错误命中： " + count);

    }
}
