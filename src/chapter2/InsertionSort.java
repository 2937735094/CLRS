package introduction.chapter2;

import introduction.Util;

import java.util.Arrays;

/**
 * 插入排序-原址(in place)排序
 * Θ(n^2)
 *
 * 思路:
 * 定义数组a[i...k]
 * 循环数组a 获取当前下标为j的元素定义为key
 * 将key和j-1...i的值遍历比较 如果key小于之前一个值x则将x后移一位
 * 直到找到合适位置t  将key放到下标为t的位置
 */
public class InsertionSort {
    public static void main(String[] args) {
        int[] ints = Util.init();
        System.out.println("Before: " + Arrays.toString(ints));
        sort1(ints);
//        sort2(ints);
//        sort3(ints);
        check(ints);
        System.out.println("After: " + Arrays.toString(ints));
    }

    /** 1.首先想到的写法 */
    private static void sort1(int[] ints) {
        for (int i = 1; i < ints.length; i++) {
            for (int j = i; j >= 0; j--) {
                if (j >= 1 && ints[j - 1] > ints[j]) {
                    int current_j_val = ints[j];
                    ints[j] = ints[j - 1];
                    ints[j - 1] = current_j_val;
                }
            }
        }
    }

    /** 2.换了种写法  可能比1少执行几个指令吧。。 */
    private static void sort2(int[] ints) {
        for (int i = 1; i < ints.length; i++) {
            int key = ints[i];
            int moveToIndex = -1; // -1作为一个警戒值 数组没有负数下标
            for (int j = i - 1; j >= 0; j--) {
                if (ints[j] > key) {
                    ints[j + 1] = ints[j];
                    moveToIndex = j;
                }
            }
            if (moveToIndex != -1) {
                ints[moveToIndex] = key;
            }
        }
    }

    /** 3.书上的伪代码实现 */
    private static void sort3(int[] ints) {
        for (int j = 1; j < ints.length; j++) {
            int key = ints[j];
            int i = j - 1;
            while (i >= 0 && ints[i] > key) {
                ints[i + 1] = ints[i];
                i = i - 1;
            }
            ints[i + 1] = key;
        }
    }

    /** 检查结果是否正确(只检查 大小顺序 不检查内容) */
    private static void check(int[] ints) {
        boolean ok = true;
        for (int i = 1; i < ints.length; i++) {
            if (ints[i] < ints[i - 1]) {
                System.out.println("结果不正确！ 查看i:" + i + "附近的数值");
                ok = false;
                break;
            }
        }
        if (ok) System.out.println("结果正确!");
    }
}
