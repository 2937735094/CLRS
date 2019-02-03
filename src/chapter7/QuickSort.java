package introduction.chapter7;

import introduction.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 快速排序-原址排序
 * 使用分治法
 * quickSort1 - 递归式
 * quickSort2 - 迭代式
 *
 * 思路:
 * 找到一个key作为主元,然后遍历数组 将小于等于key的和大于key的分别放在key的不同边  对2边的集合不断重复如此过程即可得到结果
 *
 * 最坏情况就是 T(n) = T(0) + T(n-1) + Θ(n) = Θ(n) + Θ(n^2) = Θ(n^2) [因为是等差级数]  T(n-1), T((n-1)-1)...
 * 但是期望时间为Θ(n lgn) 这里使用随机化 key不再指定为数组最后以为  采用随机算法获取数组内的任意值作为key
 * 假设E(random)能使数组两侧均匀分布(5:5分  9:1分都行  可以使用递归树理解、代换法验证)  运行时间为 T(n) = 2T(n/2) + Θ(n) = Θ(n lgn)
 * 具体可以看书上的描述和麻省理工学院公开课：算法导论-》快排及随机化算法 的讲解
 *
 * 《概率论与数理统计》 免费在线课程 https://www.bilibili.com/video/av24264750?spm_id_from=333.334.b_62696c695f746563686e6f6c6f6779.10
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] ints = Util.init(1000, 0, 1000);
        System.out.println("before: " + Arrays.toString(ints));
//        quickSort1(ints, 0, ints.length - 1);
        quickSort2(ints);
        System.out.println("after: " + Arrays.toString(ints));
        check(ints);
    }

    /**
     * 书上的例子 递归式
     */
    private static void quickSort1(int[] ints, int s_i, int f_i) {
        if (s_i < f_i) {
            int mid_i = quickSort1_Partition(ints, s_i, f_i);
            quickSort1(ints, s_i, mid_i - 1);
            quickSort1(ints, mid_i + 1, f_i);
        }
    }

    private static int quickSort1_Partition(int[] ints, int s_i, int f_i) {
        int key = ints[f_i];
//        // 这里可以使用随机化
//        int random = new Random().ints(1, s_i, f_i).sum();
//        // exchange ints[f_i] with ints[i]
//        swap(ints, f_i, random);
//        int key = ints[f_i];
        int i = s_i - 1;
        for (int j = s_i; j < f_i; j++) {
            if (ints[j] <= key) swap(ints, ++i, j);
        }
        swap(ints, ++i, f_i);
        return i;
    }

    /**
     * 迭代式
     */
    private static void quickSort2(int[] ints) {
        int p = 0;
        // stacks的深度一般不会太深  测试数十次(ints.length == 1000 且 内部元素为0-1000随机) 深度分布在10到20之间
        ArrayList<Stack> stacks = new ArrayList<>();
        int depth = 0;
        stacks.add(p++, new Stack(0, ints.length - 1));
        while (p > 0) {
            depth = Math.max(depth, stacks.size());
            Stack stack = stacks.remove(--p);
            if (stack.start >= stack.end) continue;
            int mid = ints[stack.end];
            int s_i = stack.start, f_i = stack.end - 1;
            while (s_i < f_i) {
                // 从左 和 右同时向中间收缩  如果内部判断中止(且原因在&&符号前面) 则在后续操作中更换位置
                while (ints[s_i] <= mid && s_i < f_i) s_i++;
                while (ints[f_i] > mid && s_i < f_i) f_i--;
                swap(ints, s_i, f_i);
            }
            if (ints[s_i] >= ints[stack.end]) swap(ints, s_i, stack.end);
            else s_i++;

            stacks.add(p++, new Stack(stack.start, s_i - 1));
            stacks.add(p++, new Stack(s_i + 1, stack.end));
        }
        System.out.println("stacks的最大深度为: " + depth);
    }

    private static void swap(int[] ints, int x_i, int y_i) {
        int t = ints[x_i];
        ints[x_i] = ints[y_i];
        ints[y_i] = t;
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

class Stack {
    int start;
    int end;

    Stack(int start, int end) {
        this.start = start;
        this.end = end;
    }
}