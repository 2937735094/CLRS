package introduction.chapter4;

import introduction.Util;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 二分查找(分治法)
 *
 * 思路:
 * 找到一个已排序的数组的中间值  如果key小于中间值则向左/右寻找(看具体排序),否则朝相反方向找
 * 重复之前步骤 直到找到/查找结束(没找到)
 *
 * @see java.util.Arrays#binarySearch(int[], int) ; jdk的源码实现
 * T(n) = T(n/2) + Θ(n) = Θ(lgn)
 */
public class BinarySearch {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要初始化的数组大小:");
        int[] ints = Util.init(scanner.nextInt());
        Arrays.sort(ints);
        System.out.println(Arrays.toString(ints));

        System.out.println("请输入要在数组中搜索的值:");
        int search = binarySearch1(ints, scanner.nextInt(), 0, ints.length - 1);
//        int search = binarySearch2(ints, scanner.nextInt());
        check(ints, search);
        scanner.close();
    }

    /**
     * 1.递归方式
     */
    private static int binarySearch1(int[] ints, int key, int s_i, int f_i) {
        int index = -1;
        if (s_i < f_i) {
            int mid_i = (s_i + f_i) >>> 1;

            if (ints[mid_i] == key) index = mid_i;
            else if (ints[mid_i] > key) index = binarySearch1(ints, key, s_i, mid_i);
            else index = binarySearch1(ints, key, mid_i + 1, f_i);
        }
        return index;
    }

    /**
     * 2.迭代方式
     */
    private static int binarySearch2(int[] ints, int key) {
        int index = -1;

        int s_i = 0;
        int f_i = ints.length - 1;

        while (s_i <= f_i) {
            int mid_i = (s_i + f_i) >>> 1;
            int mid_v = ints[mid_i];

            if (mid_v == key) return mid_i;
            else if (mid_v > key) f_i = mid_i - 1;
            else s_i = mid_i + 1;
        }
        return index;
    }

    private static void check(int[] ints, int search) {
        System.out.println("查找的值下标为: " + search);
        if (search >= 0) {
            System.out.println("查找的值为: " + ints[search]);
            if (search != 0) {
                System.out.println("查找的值的前一个值为: " + ints[search - 1]);
            }
            if (search + 1 != ints.length) {
                System.out.println("查找的值的后一个值为: " + ints[search + 1]);
            }
        } else {
            System.out.println("未找到指定目标");
        }
    }
}
