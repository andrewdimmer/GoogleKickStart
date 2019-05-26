/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlekickstart2019a1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner input = determineInput();
        int numberOfCases = input.nextInt();
        
        for (int i = 0; i < numberOfCases; i++) {
            int n = input.nextInt();
            int p = input.nextInt();
            ArrayList<Integer> s = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                s.add(input.nextInt());
            }
            
            Collections.sort(s);
            long sum = 0;
            for (int j = 0; j < p; j++) {
                // System.out.println((s.size()-1) + " " + p + " " + j);
                sum += s.get(p-1)-s.get(j);
            }
            for (int j = 1; j < s.size() - p + 1; j++) {
                long newSum = sum + (s.get(j+p-1)-s.get(j+p-2))*(p-1) - (s.get(j+p-2)-s.get(j-1));
                if (newSum < sum) {
                    sum = newSum;
                }
            }
            System.out.println("Case #" + (i+1) + ": " + sum);
        }
    }
    
}
