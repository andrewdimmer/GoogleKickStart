/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlekickstartpractice2018a1;

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
    
    public static Scanner determineInput(boolean localTest) {
        if (localTest) {
            try {
                return new Scanner(new FileReader("input/tests.txt"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Solution.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Scanner(System.in);
    }
    
    public static long getY(long number, int power, int previousDigit) {
        // System.out.println(power + " | " + (long)Math.pow(10,power)+ " - " + (number / (long)Math.pow(10,power)));
        // System.out.println((number % Math.pow(10,power)));
        int greatestDigit = (int)(number / (long)Math.pow(10,power));
        //System.out.println(greatestDigit);
        if (power == 0) {
            return number % 2;
        } else if (greatestDigit % 2 == 0) {
            return getY((long)(number % (long)Math.pow(10,power)), power-1, greatestDigit);
        } else {
            long restOfNumber = (long)(number % (long)Math.pow(10,power));
            // System.out.println(restOfNumber + "\t" + 5*(long)Math.pow(10,power-1));
            long breakPoint = 0;
            for (int i = power-1; i > -1; i--) {
                breakPoint += 4 * (long)Math.pow(10,i);
            }
            if (restOfNumber < breakPoint || greatestDigit == 9) {
                long compareTo = 0;
                for (int i = power-1; i > -1; i--) {
                    compareTo += 8 * (long)Math.pow(10,i);
                }
                // System.out.println(compareTo);
                if (greatestDigit == 9 && restOfNumber >= compareTo) {
                    return (number - compareTo) % (long)Math.pow(10,power) + (long)Math.pow(10,power);
                }
                return (number - compareTo) % (long)Math.pow(10,power);
            } else {
                return (long)Math.pow(10,power) - restOfNumber;
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner input = determineInput(true);
        int numberOfCases = input.nextInt();
        long[] cases = new long[numberOfCases];
        
        for (int i = 0; i < numberOfCases; i++) {
            cases[i] = input.nextLong();
            long y = getY(cases[i], 16, 0);
            System.out.println("Case #" + (i+1) + ": " + y);
        }
    }
}

