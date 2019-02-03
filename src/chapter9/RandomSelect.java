package introduction.chapter9;

import introduction.Util;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * 随机选择
 * 算法分析可以看书或者 麻省理工学院公开课：算法导论》顺序统计、中值 17分钟时开始的讲解
 * 这可以参考快速排序的分析(随机化)
 */
public class RandomSelect {

    public static void main(String[] args) {
        int[] ints = Util.init();
        System.out.println(Arrays.toString(ints) + "  长度为: " + ints.length);

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要查询第x小的值(0<x<=长度): ");
        int k = scanner.nextInt();
        if (k <= 0 || k > ints.length) {
            System.out.println("输入结果不符合要求!");
            return;
        }
        int value = randomSelect(ints, 0, ints.length - 1, k);

        System.out.println("第" + k + "小的值为:" + value);

        Arrays.sort(ints);
        System.out.println("排序后: " + Arrays.toString(ints));
    }

    private static int randomSelect(int[] ints, int s_i, int f_i, int i) {
        if (s_i == f_i || i <= 0) return ints[s_i];
        int pivot = randomPartition(ints, s_i, f_i);
        int k = pivot - s_i + 1;  // k代表第几小  如果 pivot: 5 s:i = 1 则 k = 5 -》 （1, 2, 3, 4, 5）  pivot - s_i计算的是不包含pivot自身的
        if (i == k) {
            return ints[pivot];
        } else if (i < k) {
            return randomSelect(ints, s_i, pivot - 1, i);
        } else {
            return randomSelect(ints, pivot + 1, f_i, i - k);
        }
    }

    /**
     * 该步骤中会有数组位置变更但是还是原址的
     */
    private static int randomPartition(int[] ints, int s_i, int f_i) {
        int random = new Random().ints(1, s_i, f_i).toArray()[0];
        swap(ints, f_i, random);
        int key = ints[f_i];
        int i = s_i - 1;

        for (int j = s_i; j < f_i; j++) {
            if (ints[j] <= key) swap(ints, ++i, j);
        }
        swap(ints, ++i, f_i);
        return i;
    }

    private static void swap(int[] ints, int x_i, int y_i) {
        int t = ints[x_i];
        ints[x_i] = ints[y_i];
        ints[y_i] = t;
    }
}
