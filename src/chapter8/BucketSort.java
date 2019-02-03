package introduction.chapter8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 桶排序
 */
public class BucketSort {

    /*
    书上伪代码
    BucketSort(A)
        n = A.length
        let B[0..n-1] be a new array
        for i = 0 to n-1
            make B[i] an empty list
        for i = 1 to n
            insert A[i] into list B[n*A[i]] // n*A[i]向下取整
        for i = 0 to n-1
            sort list B[i] with insertion sort
        concatenate the lists B[0], B[1],..., B[n-1] together in order
     */

    public static void main(String[] args) {
        List<Integer> ints = new Random().ints(100, 0, 1000).boxed().collect(Collectors.toList());
        System.out.println("before:" + Arrays.toString(ints.toArray()));
        bucketSort(ints);
        System.out.println("after:" + Arrays.toString(ints.toArray()));
        check(ints);
    }

    private static void bucketSort(List<Integer> ints) {
        bucketSortAux(ints, getDivisionValueOfSequence(ints));
    }

    @SuppressWarnings("unchecked")
    private static void bucketSortAux(List<Integer> ints, int divisionValue) {
        // 1 初始化
        ArrayList<Integer>[] bucketArray = new ArrayList[10];
        for (int i = 0; i < bucketArray.length; i++) bucketArray[i] = new ArrayList<>(0); // 这里0是为了省空间
        // 2 迭代待排序序列，并放到对应的桶中
        ints.forEach(v -> bucketArray[(v / divisionValue % 10)].add(v));
        // 4 迭代桶队列，对每一个桶中的数据做排序
        if (divisionValue > 1) {
            for (ArrayList<Integer> bucketDataList : bucketArray) {
                if (bucketDataList.size() > 0) insertionSort(bucketDataList);
            }
        }
        // 5.重置原数据
        ints.clear();
        for (List<Integer> list : bucketArray) {
            if (list.size() > 0) ints.addAll(list);
        }
    }

    /**
     * 求取sequence中获取到需要除以的值，返回值为10/100/1000/10000...
     */
    private static int getDivisionValueOfSequence(List<Integer> ints) {
        // 计算待排序中的最大值
        int max = 0;
        for (int target : ints) {
            max = Math.max(target, max);
        }
        // 求取第一次的除数，只会为10^n
        int divisionValue = 1;
        while (max >= 10) {
            divisionValue *= 10;
            max /= 10;
        }
        return divisionValue;
    }

    private static void insertionSort(ArrayList<Integer> ints) {
        for (int j = 1; j < ints.size(); j++) {
            int key = ints.get(j);
            int i = j - 1;
            while (i >= 0 && ints.get(i).compareTo(key) > 0) {
                ints.set(i + 1, ints.get(i));
                i--;
            }
            ints.set(i + 1, key);
        }
    }

    /**
     * 检查结果是否正确(只检查 大小顺序 不检查内容)
     */
    private static void check(List<Integer> ints) {
        boolean ok = true;
        for (int i = 1; i < ints.size(); i++) {
            if (ints.get(i).compareTo(ints.get(i - 1)) < 0) {
                System.out.println("结果不正确！ 查看i:" + i + "附近的数值");
                ok = false;
                break;
            }
        }
        if (ok) System.out.println("结果正确!");
    }
}
