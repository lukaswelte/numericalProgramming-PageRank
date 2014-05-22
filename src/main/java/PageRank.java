import java.util.Arrays;
import java.util.Comparator;

public class PageRank {

    /**
     * Diese Methode erstellt die Matrix A~ fuer das PageRank-Verfahren
     * PARAMETER:
     * L: die Linkmatrix (s. Aufgabenblatt)
     * rho: Wahrscheinlichkeit, anstatt einem Link zu folgen,
     * zufaellig irgendeine Seite zu besuchen
     */
    public static double[][] buildProbabilityMatrix(int[][] L, double rho) {
        double[][] res = new double[L.length][L.length];
        int[] anzLinks = new int[L.length];

        for (int j = 0; j < res.length; j++) {
            for (int i = 0; i < res.length; i++) {
                anzLinks[j] += L[i][j];
            }
        }
        for (int j = 0; j < L.length; j++) {

            for (int i = 0; i < L.length; i++) {
                double faktor = L[i][j] > 0 ? (1.0 / anzLinks[j]) : 0;
                res[i][j] = (1 - rho) * faktor + rho / L.length;
            }
        }

        return res;
    }

    /**
     * Diese Methode berechnet die PageRanks der einzelnen Seiten,
     * also das Gleichgewicht der Aufenthaltswahrscheinlichkeiten.
     * (Entspricht dem p-Strich aus der Angabe)
     * Die Ausgabe muss dazu noch normiert sein.
     * PARAMETER:
     * L: die Linkmatrix (s. Aufgabenblatt)
     * rho: Wahrscheinlichkeit, zufaellig irgendeine Seite zu besuchen
     * ,anstatt einem Link zu folgen.
     */
    public static double[] rank(int[][] L, double rho) {
        double[][] A = buildProbabilityMatrix(L, rho);
        for (int i = 0; i < L.length; i++) {
            A[i][i] -= 1;
        }

        double[] p = Gauss.solveSing(A);

        // normalisieren
        double sum = 0;
        for (double aP : p) {
            sum += aP;
        }

        for (int i = 0; i < p.length; i++) {
            p[i] /= sum;
        }

        return p;
    }

    /**
     * Diese Methode erstellt eine Rangliste der uebergebenen URLs nach
     * absteigendem PageRank.
     * PARAMETER:
     * urls: Die URLs der betrachteten Seiten
     * L: die Linkmatrix (s. Aufgabenblatt)
     * rho: Wahrscheinlichkeit, anstatt einem Link zu folgen,
     * zufaellig irgendeine Seite zu besuchen
     */
    public static String[] getSortedURLs(String[] urls, int[][] L, double rho) {
        int n = L.length;

        double[] p = rank(L, rho);

        RankPair[] sortedPairs = new RankPair[n];
        for (int i = 0; i < n; i++) {
            sortedPairs[i] = new RankPair(urls[i], p[i]);
        }

        Arrays.sort(sortedPairs, new Comparator<RankPair>() {

            @Override
            public int compare(RankPair o1, RankPair o2) {
                return -Double.compare(o1.pr, o2.pr);
            }
        });

        String[] sortedUrls = new String[n];
        for (int i = 0; i < n; i++) {
            sortedUrls[i] = sortedPairs[i].url;
        }

        return sortedUrls;
    }

    /**
     * Ein RankPair besteht aus einer URL und dem zugehoerigen Rang, und dient
     * als Hilfsklasse zum Sortieren der Urls
     */
    private static class RankPair {
        public String url;
        public double pr;

        public RankPair(String u, double p) {
            url = u;
            pr = p;
        }
    }
}
