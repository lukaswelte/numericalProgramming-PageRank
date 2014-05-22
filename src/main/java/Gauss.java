import java.util.Arrays;

public class Gauss {

    /**
     * Diese Methode soll die Loesung x des LGS R*x=b durch
     * Rueckwaertssubstitution ermitteln.
     * PARAMETER:
     * R: Eine obere Dreiecksmatrix der Groesse n x n
     * b: Ein Vektor der Laenge n
     */
    public static double[] backSubst(double[][] R, double[] b) {
        double[] x = new double[b.length];
        for (int i = b.length - 1; i >= 0; i--) {
            x[i] = b[i];
            for (int j = i + 1; j <= b.length - 1; j++) {
                x[i] -= R[i][j] * x[j];
            }
            x[i] /= R[i][i];
        }
        return x;
    }

    /**
     * 2D Array kopieren
     */
    public static double[][] copyArray(double[][] src) {
        int length = src.length;
        double[][] target = new double[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    private static void swapRow(double[][] a2, double[] b2, int i, int zn) {
        double[] tmp = a2[i];
        a2[i] = a2[zn];
        a2[zn] = tmp;

        double tmp2 = b2[i];
        b2[i] = b2[zn];
        b2[zn] = tmp2;
    }

    /**
     * Diese Methode soll die Loesung x des LGS A*x=b durch Gauss-Elimination mit
     * Spaltenpivotisierung ermitteln. A und b sollen dabei nicht veraendert werden.
     * PARAMETER: A:
     * Eine regulaere Matrix der Groesse n x n
     * b: Ein Vektor der Laenge n
     */
    public static double[] solve(double[][] A, double[] b) {
        double[] copyB = Arrays.copyOf(b, b.length);
        double[][] copyA = copyArray(A);

        int n = b.length - 1;
        int zn;

        // solve gauss to upper tri. matrix

        for (int i = 0; i <= n; i++) {
            // pivot
            zn = i;
            for (int j = i + 1; j <= n; j++) {
                if (Math.abs(copyA[j][i]) > copyA[zn][i])
                    zn = j;
            }
            swapRow(copyA, copyB, i, zn);

            for (int j = i + 1; j <= n; j++) {
                double a = copyA[j][i] / copyA[i][i];
                for (int k = 0; k < b.length; k++) {
                    copyA[j][k] -= a * copyA[i][k];
                }
                copyB[j] -= a * copyB[i];
            }
        }

        return backSubst(copyA, copyB);
    }

    /**
     * Diese Methode soll eine Loesung p!=0 des LGS A*p=0 ermitteln. A ist dabei
     * eine nicht invertierbare Matrix. A soll dabei nicht veraendert werden.
     * <p/>
     * Gehen Sie dazu folgendermassen vor (vgl.Aufgabenblatt):
     * -Fuehren Sie zunaechst den Gauss-Algorithmus mit Spaltenpivotisierung
     * solange durch, bis in einem Schritt alle moeglichen Pivotelemente
     * numerisch gleich 0 sind (d.h. <1E-10)
     * -Betrachten Sie die bis jetzt entstandene obere Dreiecksmatrix T und
     * loesen Sie Tx = -v durch Rueckwaertssubstitution
     * -Geben Sie den Vektor (x,1,0,...,0) zurueck
     * <p/>
     * Sollte A doch intvertierbar sein, kann immer ein Pivot-Element gefunden werden(>=1E-10).
     * In diesem Fall soll der 0-Vektor zurueckgegeben werden.
     * PARAMETER:
     * A: Eine singulaere Matrix der Groesse n x n
     */
    public static double[] solveSing(double[][] A) {
        double[] res = new double[A.length];

        double[][] copyA = copyArray(A);

        // solve gauss to upper tri. matrix

        for (int i = 0; i < A.length; i++) {
            // pivot
            int zn = i;
            for (int j = i + 1; j < A.length; j++) {
                if (Math.abs(copyA[j][i]) > copyA[zn][i])
                    zn = j;
            }
            swapRow(copyA, new double[A.length], i, zn);

            if (copyA[i][i] < Math.pow(10, -10)) {
                res[i] = 1;
                double[][] T = copyArray(copyA);
                double[] v = new double[T.length];

                for (int j = 0; j < T.length; j++) {
                    v[j] = -copyA[j][i];
                }

                double[] x = backSubst(T, v);
                System.arraycopy(x, 0, res, 0, x.length);
                return res;
            }
            for (int j = i + 1; j < A.length; j++) {
                double a = copyA[j][i] / copyA[i][i];
                for (int k = 0; k < A.length; k++) {
                    copyA[j][k] -= a * copyA[i][k];
                }
            }
        }

        return new double[A.length];
    }

    /**
     * Diese Methode berechnet das Matrix-Vektor-Produkt A*x mit A einer nxm
     * Matrix und x einem Vektor der Laenge m. Sie eignet sich zum Testen der
     * Gauss-Loesung
     */
    public static double[] matrixVectorMult(double[][] A, double[] x) {
        int n = A.length;
        int m = x.length;

        double[] y = new double[n];

        for (int i = 0; i < n; i++) {
            y[i] = 0;
            for (int j = 0; j < m; j++) {
                y[i] += A[i][j] * x[j];
            }
        }

        return y;
    }
}
