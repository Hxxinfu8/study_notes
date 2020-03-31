package com.company;

import java.util.Arrays;

/**
 * 排序算法
 */
public class Sort {
    public static void main(String[] args) {
        int[] arr = {3, 6, 7, 2, 4, 8, 0, 1};
        //bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
        //quickSort(arr, 0, arr.length - 1);
        //selectSort(arr);
        shellSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 冒泡排序
     * 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
     * 针对所有的元素重复以上的步骤，除了最后一个。
     * 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     *
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static int getMiddle(int[] numbers, int low, int high) {
        //数组的第一个作为中轴
        int temp = numbers[low];
        while (low < high) {
            while (low < high && numbers[high] > temp) {
                high--;
            }
            //比中轴小的记录移到低端
            numbers[low] = numbers[high];
            while (low < high && numbers[low] < temp) {
                low++;
            }
            //比中轴大的记录移到高端
            numbers[high] = numbers[low];
        }
        //中轴记录到尾
        numbers[low] = temp;
        return low;
    }

    // 快速排序
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int middle = getMiddle(arr, low, high);
            quickSort(arr, low, middle - 1);
            quickSort(arr, middle + 1, high);
        }
    }

    /**
     * 选择排序
     * 第一次从待排序的数据元素中选出最小（或最大）的一个元素，
     * 存放在序列的起始位置，然后再从剩余的未排序元素中寻找到最小（大）元素，
     * 然后放到已排序的序列的末尾。以此类推，直到全部待排序的数据元素的个数为零。
     * 选择排序是不稳定的排序方法
     * @param arr
     */
    public static void selectSort(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length -1; i ++) {
            int index = i;
            for (int j = 1 + i; j < arr.length; j ++) {
                if (arr[j] < arr[index]) {
                    index = j;
                }
                count ++;
            }
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
        System.out.println(count);
    }

    // 插入排序
    public static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i ++) {
            int insertValue = arr[i];
            int insertIndex = i - 1;
            while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex --;
            }
            arr[insertIndex + 1] = insertValue;
        }
    }

    // 希尔排序
    public static void shellSort(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i ++) {
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {
                        int temp = arr[j + gap];
                        arr[j + gap] = arr[j];
                        arr[j] = temp;
                    }
                }
            }
        }
    }
}
