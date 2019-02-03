package introduction.chapter4;

/**
 * 斐波那契数
 */
public class FibonacciNumbers {
    /*
      这里只说理论
      斐波那契数列如下格式   1 1 2 3 5 8 13 21 34 ....
           数列的第n项
           1. if n = 0  f(n) = 0
           2. if n = 1  f(n) = 1
           3. if n >= 1 f(n) = f(n - 1) + f(n - 2)
      引入黄金比例(Golden Ratio)
      φ = (1 + 5^(1/2)) / 2   [φ读作phi]

      如果求第n项且n >= 1,使用朴素递归运行时间大概为: T(n) = Ω(φ^n)
      指数级的运行时间是很久的,考虑如何优化为多项式级的

      这里需要提前引入第15章-动态规划(可以回头看这个类再想)  采用自下而上的方式的方式  运行时间将会节省至Θ(n)[一个线性时间]
      线性时间并不是改问题的最优解！

      这里引入一个数学技巧
      F(n) = φ^n / 5^(1/2)并取整至最接近的数

      所以最终可以优化为lgn

      但是实际情况 在使用浮点运算计算φ^n / 5^(1/2)时很困难(指操作  因为核心是讨论算法所以不具体说明)

      recursive squaring （代替上面的数学技巧） 耗时相同
      theorem(定理)  A = (B)^2
      矩阵A = -             -
              | F(n+1) F(n) |
              | F(n) F(n+1) |
              -             -

      矩阵B =  -     -
               | 1 1 |
               | 1 0 |
               -     -
     */

}
