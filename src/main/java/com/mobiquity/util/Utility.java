package com.mobiquity.util;

public class Utility {

    /**
     * Based on https://www.baeldung.com/java-knapsack
     * and adapted to handle decimals values and object indexes as response.
     *
     * @param w List of item weights being evaluated
     * @param v List of item values being evaluated
     * @param n Total object count
     * @param W Max capacity of Package to be filled
     * @return String displaying the index of items packaged into main pack
     */
    public static String knapSackAlgCalculation(Double[] w, Double[] v, int n, int W) {
        StringBuilder builder = new StringBuilder();
        if (n <= 0 || W <= 0) {
            return "";
        }

        int[][] m = new int[n + 1][W + 1];
        int[] selected = new int[n + 1];
        for (int j = 0; j <= W; j++) {
            m[0][j] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= W; j++) {
                if ((int)Math.round(w[i - 1]) > j) {
                    selected[i] = 1;
                    m[i][j] = m[i - 1][j];
                } else {
                    selected[i]=0;
                    m[i][j] = Math.max(
                            m[i - 1][j],
                            m[i - 1][j - (int)Math.round(w[i - 1])] + (int)Math.round(v[i - 1]));
                    //[j - w[i - 1]] + v[i - 1]
                }
            }
        }

        //System.out.println("\nItems selected : ");
        int tempW = W;
        int y = 0; //to index in selected
        for (int x = n; x > 0; x--){
            if ((tempW-(int)Math.round(w[x-1]) >= 0) && (m[x][tempW] - m[x-1][(tempW-(int)Math.round(w[x-1]))] == v[x-1]) ){
                selected[y++] = x-1; //store current index and increment y
                tempW-=(int)Math.round(w[x-1]);
            }
        }

        for(int j = y-1; j >= 0; j--){
            if(j != 0){
               //System.out.print((selected[j]+1) + ",");
                builder.append(selected[j] + 1).append(",");
            }else {
                //System.out.print((selected[j] + 1));
                builder.append((selected[j] + 1));
            }
        }
        //System.out.println();
        if(y == 0){
            //System.out.println("-");
            builder.append("-");

        }
        builder.append(System.getProperty("line.separator"));
        return builder.toString();
    }

}
