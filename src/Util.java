package introduction;

import java.util.Random;

public class Util {

    /**
     * 初始化数组
     */
    public static int[] init() {
        return init(20);
    }

    /**
     * 初始化数组
     */
    public static int[] init(int size) {
        return init(size, 0, 100);
    }

    /**
     * 初始化数组
     */
    public static int[] init(int size, int origin, int bound) {
        return new Random().ints(size, origin, bound).toArray();
    }
}
