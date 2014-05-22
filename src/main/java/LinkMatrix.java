import java.io.*;


public class LinkMatrix {
    // private static final String lineDelim = ";";
    private static final String delim = " ";
    public int[][] L;
    public String[] urls;

    public void read(String filename) throws IOException {
        String curRow[];

        BufferedReader file = new BufferedReader(new FileReader(filename));

        try {
            int n = Integer.valueOf(file.readLine());

            L = new int[n][n];
            for (int i = 0; i < n; i++) {
                curRow = file.readLine().split(delim);
                for (int j = 0; j < n; j++)
                    L[i][j] = Integer.valueOf(curRow[j]);
            }

            urls = new String[n];
            for (int i = 0; i < n; i++)
                urls[i] = file.readLine();
        } finally {
            file.close();
        }
    }

    public void write(String filename) throws
            IOException {
        String temp;

        BufferedWriter file = new BufferedWriter(new FileWriter(filename));

        try {
            file.write(String.valueOf(L.length) + "\r\n");

            for (int[] aL : L) {
                temp = "";
                for (int j = 0; j < L.length; j++)
                    temp += String.valueOf(aL[j]) + delim;
                file.write(temp + "\r\n");
            }

            for (String url : urls) file.write(url + "\r\n");
        } finally {
            file.close();
        }
    }
}