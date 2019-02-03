package introduction.chapter4;

import java.util.Random;

/**
 * 乘方问题 (无法实际使用  需要扩展 这里只是用来分析分治法)
 * 给定一个 x 和 n (n>=0) 求 x^n
 * <p>
 * 思路:
 * x^n 暴力求解方式为 x*x*x*x.... n个x相乘
 * 使用分治法后转换为  ((x * x) * (x * x)) * ((x * x) * (x * x)) --> 简写后为 (((x^2)^2)^2... (不断的让递归结果相乘)
 */
public class PoweringANumber {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Random random = new Random();
            int x = random.nextInt(10); //尽量减少这个值  没有预防溢出
            int n = random.nextInt(10);

            System.out.println("x: " + x + "  n: " + n);

            long result = powering_a_number(x, n);
            if (result >= 0) System.out.println("分治法求出的结果为(int): " + result);
            else System.out.println("计算溢出");

            if (result >= 0) System.out.println(result == Double.valueOf(Math.pow(x, n)).longValue() ? "计算正确" : "计算错误");

        }
    }

    private static long powering_a_number(long x, long n) {
        if (n == 0) return 1;
        else if (n == 1) return x;
        else if (n == 2) return x * x;

        if ((n & 1) == 0) return powering_a_number(powering_a_number(x, n >>> 1), 2);
        else return powering_a_number(powering_a_number(x, (n - 1) >>> 1), 2) * x;
    }
}
