/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlekickstart2019a3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
            return new Scanner(new FileReader("input/tests2.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Solution.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Scanner(System.in);
    }
    
    private static class comparator implements Comparator<int[]> {
        @Override
        public int compare(int[] b1, int[] b2) {
            if (b1[1] != b2[1]) {
                return b1[1] - b2[1];
            } else {
                return b2[0] - b1[0];
            }
        }
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
            int q = input.nextInt();
            ArrayList<int[]> bookings = new ArrayList<>();
            for (int j = 0; j < q; j++) {
                int[] b = new int[2];
                b[0] = input.nextInt();
                b[1] = input.nextInt();
                bookings.add(b);
            }
            Comparator sorter = new comparator();
            Collections.sort(bookings, sorter);
            ArrayList<int[]> booked = new ArrayList<>();
            booked.add(bookings.remove(0));
            // System.out.println("[" + booked.get(0)[0] + "," + booked.get(0)[1] + "]");
            int k = booked.get(0)[1] - booked.get(0)[0] +1;
            // System.out.println(k);
            for (int[] b : bookings) {
                // System.out.println("[" + b[0] + "," + b[1] + "]");
                int kmax = b[1] - b[0] + 1;
                int newL = b[0], newR = b[1];
                for (int rangeNumber = 0; rangeNumber < booked.size(); rangeNumber++) {
                    int[] range = booked.get(rangeNumber);
                    // System.out.println("--> [" + range[0] + "," + range[1] + "]");
                    if (range[1] >= b[0]) {
                        if (range[0] >= b[0]) {
                            kmax -= range[1] - range[0] + 1;
                            // System.out.println("Match 1.1");
                            booked.remove(range);
                            rangeNumber--;
                        } else {
                            kmax -= range[1] - b[0] + 1;
                            newL = range[0];
                            // System.out.println("Match 1.2");
                            booked.remove(range);
                            rangeNumber--;
                        }
                    } else if (range[0] <= b[1]) {
                        if (range[0] >= b[0]) {
                            kmax -= range[1] - range[0] + 1;
                            // System.out.println("Match 2.1");
                            booked.remove(range);
                            rangeNumber--;
                        } else if (range[1] >= b[0]) {
                            kmax -= b[1] - range[0] + 1;
                            newR = range[1];
                            // System.out.println("Match 2.2");
                            booked.remove(range);
                            rangeNumber--;
                        }
                    } else if (range[0] < b[1]) {
                        // System.out.println("Match Break");
                        break;
                    }
                    if (kmax == 0) {
                        k = 0;
                        break;
                    }
                }
                if (k == 0) {
                    break;
                } else if (kmax < k) {
                    k = kmax;
                }
                booked.add(new int[]{newL, newR});
                Collections.sort(booked, sorter);
                /*for(int[] bn : booked) {
                    System.out.println("\t[" + bn[0] + "," + bn[1] + "]");
                }*/
            }
            System.out.println("Case #" + (i+1) + ": " + k);
        }
    }
}