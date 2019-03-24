/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlekickstartpractice2018a2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew
 */
public class Solution {
    
    public static Scanner determineInput() {
        try {
            return new Scanner(new FileReader("input/tests.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Solution.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Scanner(System.in);
    }
    
    public static double output(int k, int[] v) {
        double[] baseP = new double[v.length];
        for (int i = 0; i < v.length; i++) {
            baseP[i] = (double)1/v.length;
        }
        double baseE = e(v, baseP);
        if (k == 0) {
            return baseE;
        }
        int countLowBaseE = 0;
        int countHighBaseE = 0;
        for (int i = 0; i < v.length; i++) {
            if (v[i] > baseE) {
                countHighBaseE++;
            } else {
                countLowBaseE++;
            }
        }
        double[] advP = new double[v.length];
        for (int i = 0; i < v.length; i++) {
            if (v[i] > baseE) {
                advP[i] = (1-Math.pow(((double)countLowBaseE/v.length),(k+1)))/countHighBaseE;
            } else {
                advP[i] = Math.pow(((double)countLowBaseE/v.length),(k+1))/countLowBaseE;
            }
        }
        return e(v, advP);
    }
    
    public static double e(int[] v, double[] p) {
        double e = 0;
        for (int i = 0; i < v.length; i++) {
            System.out.print(v[i] + "|" + p[i] + " ");
            e += v[i] * p[i];
        }
        return e;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner input = determineInput();
        int t = input.nextInt();
        for (int i = 0; i < t; i++) {
            int n = input.nextInt();
            int k = input.nextInt();
            int[] v = new int[n];
            for (int j = 0; j < n; j++) {
                v[j] = input.nextInt();
            }
            double y = output(k, v);
            System.out.println("Case #" + (i+1) + ": " + y);
        }
    }
    
}
