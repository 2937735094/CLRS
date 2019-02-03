package introduction.chapter15;

import java.util.Arrays;
import java.util.Random;

/**
 * 最长公共子序列
 */
public class LongestCommonSubSequence {
    public static void main(String[] args) {
        char[] x = getRandomString(50).toCharArray();
        char[] y = getRandomString(20).toCharArray();
        System.out.println("x: " + Arrays.toString(x));
        System.out.println("y: " + Arrays.toString(y));

        /*
        简单的描述一下
        假设 x[0..m]  y[0..n] 是2个序列
        c[i][j] 其实记录的是在 表格c当前位置  序列 x* 和 y* 的lcs的长度  x*是x的一个子序列表示[0..i]  y*是y的子序列表示[0..j]
        那么x 和 y 的lcs最长的时候是哪呢？  当然就是 c[m][n]

        书上15.9注意下就能写出代码  结合图15-8理解书上使用自底向上方式伪代码
        如果要证明15.9可以使用剪贴法 书上第一步有分析步骤

        实际上如下代码的实现仍然可以优化
        c可以使用更小的空间实现打印
        因为每次比较的时候 c只用了当前行和上一行
         */

        // 调用lcs的方式
        String[][] b = new String[x.length + 1][y.length + 1]; // b是可以优化掉的
        int[][] c = new int[x.length + 1][y.length + 1];
        lcs(x, y, b, c);
        System.out.print("最长公共子序列: ");
        printLCS(b, x, x.length, y.length);
        System.out.println();
//        printTable(x, y, b, c);

//        // 改良
//        int[][] c = new int[x.length + 1][y.length + 1];
//        lcs_new(x, y, c);
//        System.out.print("最长公共子序列: ");
//        print_lcs_new(x, y, c, x.length, y.length);
//        System.out.println();
////        printTable(x, y, null, c);
    }

    /**
     * 书上的伪代码具象化 需要
     */
    private static void lcs(char[] x, char[] y, String[][] b, int[][] c) {
        for (int i = 1; i <= x.length; i++) {
            b[i][0] = "◇";
        }
        for (int i = 0; i <= y.length; i++) {
            b[0][i] = "◇";
        }
        for (int i = 1; i <= x.length; i++) {
            for (int j = 1; j <= y.length; j++) {
                System.out.println("i = " + i + " j = " + j + "  c[i][j] = " + c[i][j]);
                if (x[i - 1] == y[j - 1]) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                    b[i][j] = "↖";
                } else if (c[i - 1][j] >= c[i][j - 1]) {
                    c[i][j] = c[i - 1][j];
                    b[i][j] = "↑";
                } else {
                    c[i][j] = c[i][j - 1];
                    b[i][j] = "←";
                }
            }
        }
    }

    /**
     * 打印lcs方法
     */
    private static void printLCS(String[][] b, char[] x, int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }
        switch (b[i][j]) {
            case "↖":
                printLCS(b, x, i - 1, j - 1);
                System.out.print(x[i - 1] + " ");
                break;
            case "↑":
                printLCS(b, x, i - 1, j);
                break;
            default:
                printLCS(b, x, i, j - 1);
                break;
        }
    }

    /**
     * 改良后的获取lcs的方法
     */
    private static void lcs_new(char[] x, char[] y, int[][] c) {
        for (int i = 1; i <= x.length; i++) {
            for (int j = 1; j <= y.length; j++) {
                if (x[i - 1] == y[j - 1]) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                } else if (c[i][j - 1] > c[i - 1][j]) {
                    c[i][j] = c[i][j - 1];
                } else {
                    c[i][j] = c[i - 1][j];
                }
            }
        }
    }

    /**
     * 改良后的打印lcs方法
     */
    private static void print_lcs_new(char[] x, char[] y, int[][] c, int i, int j) {
        if (i == 0 || j == 0) return;
        if (x[i - 1] == y[j - 1]) {
            print_lcs_new(x, y, c, i - 1, j - 1);
            System.out.print(x[i - 1] + " ");
        } else if (c[i - 1][j] >= c[i][j - 1]) {
            print_lcs_new(x, y, c, i - 1, j);
        } else {
            print_lcs_new(x, y, c, i, j - 1);
        }
    }

    /**
     * 打印表格数据
     */
    private static void printTable(char[] x, char[] y, String[][] b, int[][] c) {
        if (b != null) {
            System.out.println("表格B如下(行为x 列为y)：");
            for (int i = 0; i < y.length; i++) {
                if (i == 0) {
                    System.out.print("╪（yi）");
                }
                System.out.print("（" + y[i] + "）");
            }
            System.out.println();
            for (int i = 0; i < b.length; i++) {
                if (i == 0) System.out.print("xi  ");
                else System.out.print(x[i - 1] + "   ");
                for (String row : b[i]) {
                    System.out.print(row + "   ");
                }
                System.out.println();
            }
        }
        if (c != null) {
            System.out.println("表格C如下(行为x 列为y): ");
            for (int i = 0; i < y.length; i++) {
                if (i == 0) {
                    System.out.print("╪（yi）");
                }
                System.out.print("（" + y[i] + "）");
            }
            System.out.println();
            for (int i = 0; i < c.length; i++) {
                if (i == 0) System.out.print("xi  ");
                else System.out.print(x[i - 1] + "   ");
                for (int row : c[i]) {
                    System.out.print("（" + row + "）");
                }
                System.out.println();
            }
        }
    }

    private static String getRandomString(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        new Random().ints(length, 1, 26).forEach(e -> builder.append(str.charAt(e)));
        return builder.toString();
    }
}