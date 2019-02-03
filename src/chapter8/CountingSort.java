package introduction.chapter8;

import introduction.Util;

import java.util.Arrays;

/**
 * 计数排序
 */
public class CountingSort {
    /*
    伪代码(书上拿过来的)  如下写法需要确认B和C数组的容量问题  如果随机的数字过大。。。
    CountingSort(A, B, k)
        let C[0..k] be a new array
        for i = 0 to k
            C[i] = 0
        经过如下循环 C[i]包含了等于i的元素数
        for j = 1 to A.length
            C[A[j]] = C[A[j]] + 1
        经过如下循环 C[i]包含了小于等于i的元素数
        for i = 1 to k
            C[i] = C[i] + C[i - 1]
        for j = A.length downto 1
            B[C[A[j]]] = A[j]
            C[A[j]] = C[A[j]] - 1
     */

    /**
     * 没有处理负数  目前只支持0 U 正整数
     */
    public static void main(String[] args) {
        int bound = 100;
        int[] ints = Util.init(50, 0, bound);
        System.out.println("before: " + Arrays.toString(ints));
        ints = countingSort(ints, bound);
        System.out.println("after: " + Arrays.toString(ints));
    }

    /**
     * 计数排序
     *
     * @param ints     待排序数组
     * @param maxValue 数组中的最大值
     * @return 一个新的数组
     */
    private static int[] countingSort(int[] ints, int maxValue) {
        int[] b = new int[ints.length]; // 排序后的
        int[] c = new int[maxValue]; // 计数器
        for (int anInt : ints) {
            c[anInt] = c[anInt] + 1;
        }
        for (int i = 1; i < c.length; i++) {
            c[i] = c[i] + c[i - 1];
        }
        for (int i = ints.length - 1; i >= 0; i--) {
            b[c[ints[i]] - 1] = ints[i];
            c[ints[i]] = c[ints[i]] - 1;
        }
        return b;
    }
}
