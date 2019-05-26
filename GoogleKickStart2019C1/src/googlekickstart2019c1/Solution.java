/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlekickstart2019c1;

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
 * @author Andrew Dimmer
 * Solution to the Google Kick Start 2019 Round C Wiggle Walk Problem
 * https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050ff2/0000000000150aac
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
    
    private static class MyComparator implements Comparator<int[]> {
        @Override
        public int compare(int[] a1, int[] a2) {
            return a1[0] - a2[0];
        }
    }
    
    private static void combineRanges(ArrayList<int[]> array) {
        for (int index = array.size() - 1; index > 0 ; index--) {
            if ((array.get(index)[0])-1 == array.get(index-1)[1]) {
                array.get(index-1)[1] = array.get(index)[1];
                array.remove(index);
            }
        }
    }
    
    private static int[] contains(ArrayList<int[]> array, int index) {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i)[0] > index) {
                return null;
            }
            if (array.get(i)[1] >= index) {
                return array.get(i);
            }
        }
        return null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Comparator comp = new MyComparator();
        
        Scanner input = determineInput();
        int numberOfCases = input.nextInt();
        
        for (int i = 0; i < numberOfCases; i++) {
            ArrayList<ArrayList<int[]>> rows = new ArrayList<>();
            ArrayList<ArrayList<int[]>> cols = new ArrayList<>();
            
            int n = input.nextInt();
            int r = input.nextInt();
            int c = input.nextInt();
            int cr = (input.nextInt())-1;
            int cc = (input.nextInt())-1;
            // System.out.println(n + " " + r + " " + c + " " + cr + " " + cc);          // Debug
            
            for (int j = 0; j < r; j++) {
                ArrayList<int[]> blank = new ArrayList<>();
                rows.add(blank);
            }
            
            for (int j = 0; j < c; j++) {
                ArrayList<int[]> blank = new ArrayList<>();
                cols.add(blank);
            }
            
            //Initialize Start Position
            rows.get(cr).add(new int[]{cc,cc});
            cols.get(cc).add(new int[]{cr,cr});
            
            input.nextLine();
            String instructions = input.nextLine();
            
            for (int instruct = 0; instruct < instructions.length(); instruct++) {
                char cinstr = instructions.charAt(instruct);
                if (cinstr == 'E' || cinstr == 'W') {
                    int[] range = contains(rows.get(cr),cc);
                    if (range == null) {
                        // System.out.println("Odd error");          // Debug
                        rows.get(cr).add(new int[]{cc,cc});
                    } else {
                        // System.out.print("r" + cr + ": [" + range[0] + "," + range[1] + "] -->");          // Debug
                        if (cinstr == 'W') {
                            cc = range[0]-1;
                            range[0]--;
                        } else {
                            cc = range[1]+1;
                            range[1]++;
                        }
                        cols.get(cc).add(new int[]{cr,cr});
                    }
                } else {
                    int[] range = contains(cols.get(cc),cr);
                    if (range == null) {
                        // System.out.println("Odd error");          // Debug
                        rows.get(cc).add(new int[]{cr,cr});
                    } else {
                        // System.out.print("c" + cc + ": [" + range[0] + "," + range[1] + "] -->");          // Debug
                        if (cinstr == 'N') {
                            cr = range[0]-1;
                            range[0]--;
                        } else {
                            cr = range[1]+1;
                            range[1]++;
                        }
                        rows.get(cr).add(new int[]{cc,cc});
                    }
                }
                Collections.sort(rows.get(cr),comp);
                Collections.sort(cols.get(cc),comp);
                combineRanges(rows.get(cr));
                combineRanges(cols.get(cc));
                // System.out.println(cr + " " + cc);          // Debug
            }
            
            System.out.println("Case #" + (i+1) + ": " + (cr+1) + " " + (cc+1));
        }
    }
    
}
