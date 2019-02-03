package introduction.chapter4;

/**
 * 矩阵的乘法
 * <p>
 * 蛮力法: Ω(n^3)
 * 使用分治法 运算时间为 T(n) = 8T(n/2) + Θ(n^2)    Θ(n^3)
 * 使用Strassen 运算时间 T(n) = 7T(n/2) + Θ(n^2)    Θ(n^(lg7))
 */
// Strassen算法
public class Strassen {
    /* 看书吧。。 写起来太麻烦了
        《算法导论》第43-47页
        麻省理工学院公开课：算法导论 第3课 40-50分钟的区间内

        麻省理工学院公开课：算法导论地址: http://open.163.com/movie/2010/12/8/U/M6UTT5U0I_M6V2T998U.html

        以下代码是从互联网复制的 没有经过测试(忙着吃饭)
        https://blog.csdn.net/xlinsist/article/details/49514679
     */
    public static void main(String[] args) {
        int[][] aelements = {{1, 3, 4, 5}, {7, 2, 6, 5}, {2, 4, 2, 4}, {6, 4, 8, 2}};
        Matrix a = new Matrix(aelements);

        int[][] belements = {{6, 4, 5, 8}, {4, 2, 3, 2}, {1, 1, 1, 3}, {6, 9, 2, 7}};
        Matrix b = new Matrix(belements);

        Matrix r = recursiveMultiply(a, b);
        System.out.println("----这是结果----");
        r.printMatrix();
    }

    private static Matrix recursiveMultiply(Matrix a, Matrix b) {
        Matrix c = new Matrix(a.getRows(), a.getColumns());

        if (c.getRows() == 1)
            c.getElements()[0][0] = a.getElements()[a.getRowStartIndex()][a.getColumnStartIndex()]
                    * b.getElements()[b.getRowStartIndex()][b.getColumnStartIndex()]; // base
            // case
        else {
            Matrix[] amatrixs = MatrixMultiply.partition(a);
            Matrix a11 = amatrixs[0];
            Matrix a12 = amatrixs[1];
            Matrix a21 = amatrixs[2];
            Matrix a22 = amatrixs[3];

            Matrix[] bmatrixs = MatrixMultiply.partition(b);
            Matrix b11 = bmatrixs[0];
            Matrix b12 = bmatrixs[1];
            Matrix b21 = bmatrixs[2];
            Matrix b22 = bmatrixs[3];

            Matrix s1 = calculate(b12, b22, "-"); // s1为堂堂正正的一个矩阵，并没有用下标限制
            Matrix s2 = calculate(a11, a12, "+");
            Matrix s3 = calculate(a21, a22, "+");
            Matrix s4 = calculate(b21, b11, "-");
            Matrix s5 = calculate(a11, a22, "+");
            Matrix s6 = calculate(b11, b22, "+");
            Matrix s7 = calculate(a12, a22, "-");
            Matrix s8 = calculate(b21, b22, "+");
            Matrix s9 = calculate(a11, a21, "-");
            Matrix s10 = calculate(b11, b12, "+");

            Matrix p1 = recursiveMultiply(a11, s1);
            Matrix p2 = recursiveMultiply(s2, b22);
            Matrix p3 = recursiveMultiply(s3, b11);
            Matrix p4 = recursiveMultiply(a22, s4);
            Matrix p5 = recursiveMultiply(s5, s6);
            Matrix p6 = recursiveMultiply(s7, s8);
            Matrix p7 = recursiveMultiply(s9, s10);

            Matrix c11 = calculate(calculate(p5, p4, "+"), calculate(p6, p2, "-"), "+");
            Matrix c12 = calculate(p1, p2, "+");
            Matrix c21 = calculate(p3, p4, "+");
            Matrix c22 = calculate(calculate(p5, p1, "+"), calculate(p7, p3, "+"), "-");

            c = MatrixMultiply.merge(c11, c12, c21, c22);
        }
        return c;

    }

    private static Matrix calculate(Matrix b12, Matrix b22, String operator) {
        Matrix matrix = new Matrix(b12.getRows(), b12.getColumns());
        int[][] resultElements = matrix.getElements();
        int rp = 0;
        int cp = 0;

        int[][] aelements = b12.getElements();

        int[][] belements = b22.getElements();
        int brp = b22.getRowStartIndex();
        int bcp = b22.getColumnStartIndex();

        for (int i = b12.getRowStartIndex(); i <= b12.getRowEndIndex(); i++) {
            for (int j = b12.getColumnStartIndex(); j <= b12.getColumnEndIndex(); j++) {
                if ("-".equals(operator))
                    resultElements[rp][cp] = aelements[i][j] - belements[brp][bcp];
                else
                    resultElements[rp][cp] = aelements[i][j] + belements[brp][bcp];
                bcp++;
                cp++;
            }
            cp = 0;
            bcp = b22.getColumnStartIndex();
            brp++;
            rp++;
        }

        return matrix;
    }
}

// 分治思想
class MatrixMultiply {
    public static void main(String[] args) {
        int[][] aelements = {{1, 3, 4, 5}, {7, 2, 6, 5}, {2, 4, 2, 4}, {6, 4, 8, 2}};
        Matrix a = new Matrix(aelements);
        a.printMatrix();
        System.out.println("-------------------");

        int[][] belements = {{6, 4, 5, 8}, {4, 2, 3, 2}, {1, 1, 1, 3}, {6, 9, 2, 7}};
        Matrix b = new Matrix(belements);
        b.printMatrix();
        System.out.println("-------------------");

        Matrix r = recursiveMultiply(a, b);
        r.printMatrix();
    }

    private static Matrix recursiveMultiply(Matrix a, Matrix b) {
        Matrix c = new Matrix(a.getRows(), a.getColumns());  //根据矩阵a的起始下标......等创建的，并不是用下标约束，而是一个真实的矩阵

        if (c.getRows() == 1)
            c.getElements()[0][0] = a.getElements()[a.getRowStartIndex()][a.getColumnStartIndex()] * b.getElements()[b.getRowStartIndex()][b.getColumnStartIndex()];  // base case
        else {
            Matrix[] amatrixs = partition(a);
            Matrix a11 = amatrixs[0];
            Matrix a12 = amatrixs[1];
            Matrix a21 = amatrixs[2];
            Matrix a22 = amatrixs[3];

            Matrix[] bmatrixs = partition(b);
            Matrix b11 = bmatrixs[0];
            Matrix b12 = bmatrixs[1];
            Matrix b21 = bmatrixs[2];
            Matrix b22 = bmatrixs[3];

//            Matrix[] cmatrixs = partition(c);  //这些小矩阵的elements对象是与c一样的，只不过用下标将其限制住了，它们和c相比也不是同一个对象
//            Matrix c11 = cmatrixs[0];
//            Matrix c12 = cmatrixs[1];
//            Matrix c21 = cmatrixs[2];
//            Matrix c22 = cmatrixs[3];

            Matrix c11 = Matrix.add(recursiveMultiply(a11, b11), recursiveMultiply(a12, b21));
            Matrix c12 = Matrix.add(recursiveMultiply(a11, b12), recursiveMultiply(a12, b22));
            Matrix c21 = Matrix.add(recursiveMultiply(a21, b11), recursiveMultiply(a22, b21));
            Matrix c22 = Matrix.add(recursiveMultiply(a21, b12), recursiveMultiply(a22, b22));

            c = merge(c11, c12, c21, c22);
        }

        return c;
    }

    /**
     * 把4个小矩阵合并成一个大矩阵
     */
    static Matrix merge(Matrix c11, Matrix c12, Matrix c21, Matrix c22) {
        Matrix matrix = new Matrix(c11.getRows() * 2, c11.getColumns() * 2);
        int[][] elements = matrix.getElements();
        int length = c11.getElements().length;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                elements[i][j] = c11.getElements()[i][j];
                elements[i][j + length] = c12.getElements()[i][j];
                elements[i + length][j] = c21.getElements()[i][j];
                elements[i + length][j + length] = c22.getElements()[i][j];
            }
        }

        return matrix;
    }

    /**
     * 把一个大矩阵切分成四个小矩阵封装到数组之中
     */
    static Matrix[] partition(Matrix matrix) {
        Matrix[] matrixs = new Matrix[4];

        int rowStart = matrix.getRowStartIndex();
        int rowEnd = matrix.getRowEndIndex();
        int rowMid = (rowStart + rowEnd) / 2;

        int[][] elements = matrix.getElements();

        int columnStart = matrix.getColumnStartIndex();
        int columnEnd = matrix.getColumnEndIndex();
        int columnMid = (columnStart + columnEnd) / 2;

        matrixs[0] = new Matrix(rowStart, rowMid, columnStart, columnMid, elements);
        matrixs[1] = new Matrix(rowStart, rowMid, columnMid + 1, columnEnd, elements);
        matrixs[2] = new Matrix(rowMid + 1, rowEnd, columnStart, columnMid, elements);
        matrixs[3] = new Matrix(rowMid + 1, rowEnd, columnMid + 1, columnEnd, elements);

        return matrixs;
    }
}

/**
 * 矩阵类
 * 之所以设置起始和结束下标是因为会去用下标partition矩阵
 */
// 矩阵类
class Matrix {
    /**
     * 矩阵的行起始下标
     * 用于partition矩阵
     */
    private int rowStartIndex;

    /**
     * 矩阵的行结束下标
     * 用于partition矩阵
     */
    private int rowEndIndex;

    /**
     * 矩阵的列起始下标
     * 用于partition矩阵
     */
    private int columnStartIndex;

    /**
     * 矩阵的列结束下标
     * 用于partition矩阵
     */
    private int columnEndIndex;

    /**
     * 矩阵中的数元素
     */
    private int[][] elements;

    /**
     * 根据给定的二维数组构建一个矩阵
     */
    Matrix(int[][] elements) {
        this(0, elements.length - 1, 0, elements[0].length - 1, elements);
    }

    /**
     * 根据给定的4个下标来拆分给定的数组元素，并构建矩阵，矩阵中的元素与数组中的元素不一定一致
     */
    Matrix(int rowStartIndex, int rowEndIndex, int columnStartIndex, int columnEndIndex, int[][] elements) {
        this.rowStartIndex = rowStartIndex;
        this.rowEndIndex = rowEndIndex;
        this.columnStartIndex = columnStartIndex;
        this.columnEndIndex = columnEndIndex;
        this.elements = elements;
    }

    /**
     * 构造一个指定行和列的空矩阵
     */
    Matrix(int row, int column) {
        this(new int[row][column]);
    }


    static Matrix add(Matrix a, Matrix b) {
        Matrix matrix = new Matrix(a.getRows(), a.getColumns());
        int[][] resultElements = matrix.getElements();
        int[][] aelements = a.getElements();
        int[][] belements = b.getElements();

        for (int i = 0; i < belements.length; i++)
            for (int j = 0; j < belements.length; j++)
                resultElements[i][j] = aelements[i][j] + belements[i][j];

        return matrix;
    }

    /**
     * 根据当前矩阵的4个下标打印矩阵
     */
    void printMatrix() {
        int[][] e = this.getElements();
        for (int i = this.getRowStartIndex(); i <= this.getRowEndIndex(); i++) {
            for (int j = this.getColumnStartIndex(); j <= this.getColumnEndIndex(); j++) {
                System.out.print(e[i][j] + "  ");
            }
            System.out.println();
        }
    }

    /**
     * 获取矩阵的行
     */
    int getRows() {
        return rowEndIndex - rowStartIndex + 1;
    }

    /**
     * 获取矩阵的列
     */
    int getColumns() {
        return columnEndIndex - columnStartIndex + 1;
    }

    int getRowStartIndex() {
        return rowStartIndex;
    }

//    public void setRowStartIndex(int rowStartIndex) {
//        this.rowStartIndex = rowStartIndex;
//    }

    int getRowEndIndex() {
        return rowEndIndex;
    }

//    public void setRowEndIndex(int rowEndIndex) {
//        this.rowEndIndex = rowEndIndex;
//    }

    int getColumnStartIndex() {
        return columnStartIndex;
    }

//    public void setColumnStartIndex(int columnStartIndex) {
//        this.columnStartIndex = columnStartIndex;
//    }

    int getColumnEndIndex() {
        return columnEndIndex;
    }

//    public void setColumnEndIndex(int columnEndIndex) {
//        this.columnEndIndex = columnEndIndex;
//    }

    int[][] getElements() {
        return elements;
    }

//    public void setElements(int[][] elements) {
//        this.elements = elements;
//    }
}
