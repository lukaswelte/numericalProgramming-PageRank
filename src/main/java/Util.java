public class Util {

    /* zu Vergleichszwecken ein schaerferer Wert als in der Angabe */
    public static double eps = 1E-14;

    public static void printMatrix(int[][] A) {
        for (int[] aA : A) {
            for (int anAA : aA) System.out.print(" " + anAA);// System.out.printf("%d\t",
            // A[i][j]);
            System.out.println();
        }
    }

    public static void printMatrix(double[][] A) {
        for (double[] aA : A) {
            for (double anAA : aA) System.out.printf("%f  ", anAA);
            System.out.println();
        }
    }

    public static void printVector(double[] v) {
        for (double aV : v) System.out.printf("%f\n", aV);
    }

    public static void printStringArray(String[] a) {
        for (String anA : a) System.out.println(anA);
    }

    public static boolean vectorCompare(double[] v, double[] w) {
        if (v.length != w.length)
            return false;
        for (int i = 0; i < v.length; i++)
            if (Math.abs(v[i] - w[i]) > eps)
                return false;

        return true;
    }

    public static boolean matrixCompare(double[][] A, double[][] B) {
        if (A.length != B.length)
            return false;
        for (int i = 0; i < A.length; i++) {
            if (A[i].length != B[i].length)
                return false;
            for (int j = 0; j < A[i].length; j++)
                if (Math.abs(A[i][j] - B[i][j]) > eps)
                    return false;
        }

        return true;
    }

    public static boolean rankingCompare(String[] v, String[] w) {
        if (v.length != w.length)
            return false;
        for (int i = 0; i < v.length; i++)
            if (v[i].compareToIgnoreCase(w[i]) != 0)
                return false;

        return true;
    }
}
