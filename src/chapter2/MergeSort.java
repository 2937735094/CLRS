package introduction.chapter2;

import introduction.Util;

import java.util.Arrays;

/**
 * 归并排序-非原址排序 (分治法 第4章主要讲分治策略)
 *
 * master method: T(n) = aT(n/b) + f(n);
 *
 * 归并排序使用主方法  T(n) = 2T(n/2) + Θ(n) = Θ(n lgn)
 *
 * 思路:
 * 分治-合并
 * 将一个大的问题不断分割成问题相同但规模不同的子问题  直到分解为单个元素
 * 然后开始比较合并
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] ints = Util.init();
        System.out.println("Before: " + Arrays.toString(ints));

        sort(ints, 0, ints.length - 1);
        check(ints);

        System.out.println("After: " + Arrays.toString(ints));
    }

    /**
     * 归并排序入口
     * @param ints 待排序数组
     * @param s_i 数组首位下标
     * @param f_i 数组末位下标
     */
    private static void sort(int[] ints, int s_i, int f_i) {
        if (s_i < f_i) {
            int mid_i = Math.floorDiv((s_i + f_i), 2);
            sort(ints, s_i, mid_i);
            sort(ints, mid_i + 1, f_i);

            merge1(ints, s_i, mid_i, f_i);
//            merge2(ints, s_i, mid_i, f_i);
        }
    }

    /**
     * 1.第一想法
     * @param ints 待排序数组
     * @param s_i 数组首位下标
     * @param mid_i 分割点下标
     * @param f_i 数组末位下标
     */
    private static void merge1(int[] ints, int s_i, int mid_i, int f_i) {
        int[] new_ints = new int[f_i - s_i + 1];
        int i = s_i, j = mid_i + 1, k = 0;
        while (i <= mid_i && j <= f_i) {
            if (ints[i] < ints[j]) new_ints[k++] = ints[i++];
            else new_ints[k++] = ints[j++];
        }
        while (i <= mid_i) new_ints[k++] = ints[i++];
        while (j <= f_i) new_ints[k++] = ints[j++];
        if (new_ints.length >= 0) System.arraycopy(new_ints, 0, ints, s_i, new_ints.length);
    }

    /**
     * 2.书中的示例加上一些改动
     * @param ints 待排序数组
     * @param s_i 数组首位下标
     * @param mid_i 分割点下标
     * @param f_i 数组末位下标
     */
    private static void merge2(int[] ints, int s_i, int mid_i, int f_i) {
        int[] ints1 = Arrays.copyOfRange(ints, s_i, s_i + (mid_i - s_i + 1));
        int[] ints2 = Arrays.copyOfRange(ints, mid_i + 1, (mid_i + 1) + (f_i - mid_i));

        int i = 0, j = 0;
        for (int k = s_i; k <= f_i; k++) {
            if (i != ints1.length && j != ints2.length) {
                if (ints1[i] <= ints2[j]) ints[k] = ints1[i++];
                else ints[k] = ints2[j++];
            } else {
                // 使用如下代码代替书中的哨兵
                if (i != ints1.length) ints[k] = ints1[i++];
                else if (j != ints2.length) ints[k] = ints2[j++];
            }
        }
    }

    /**
     * 检查结果是否正确(只检查 大小顺序 不检查内容)
     */
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
