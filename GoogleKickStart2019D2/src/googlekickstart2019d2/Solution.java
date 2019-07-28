/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlekickstart2019d2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew Dimmer
 * Solution to the Google Kick Start 2019 Round D Latest Guests Problem
 * https://codingcompetitions.withgoogle.com/kickstart/round/0000000000051061/0000000000161427
 */
public class Solution {
    
    private static Scanner determineInput() {
        try {
            return new Scanner(new FileReader("input/tests1.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Solution.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Scanner(System.in);
    }
    
    private static void countGuests(ArrayList<Integer>[] clockwise, ArrayList<Integer>[] counterclockwise, int[] guestCount) {
        int firstCounterClockwise = counterclockwise.length - 1;
        for (; firstCounterClockwise >= -1; firstCounterClockwise--) {
            if (firstCounterClockwise == -1) {
                break;
            }
            if (counterclockwise[firstCounterClockwise].size() > 0) {
                break;
            }
        }
        if (firstCounterClockwise > -1) {
            int count = 1;
            int currentSet = 0;
            int countAtFirstClockwise = 0;
            int countAtLatestClockwise = 0;
            for (int i = 1; i <= clockwise.length; i++) {
                if (clockwise[(firstCounterClockwise + i) % clockwise.length].size() > 0) {
                    if (countAtLatestClockwise == 0) {
                        countAtFirstClockwise = count + 1;
                    }
                    countAtLatestClockwise = count;
                }
                if (counterclockwise[(firstCounterClockwise + i) % clockwise.length].size() > 0) {
                    for (Integer j : counterclockwise[(firstCounterClockwise + currentSet) % clockwise.length]) {
                        // System.out.println(j + " = " + count + " (count)");          // Debug
                        if (guestCount[j] > count - countAtLatestClockwise + countAtFirstClockwise/2) {
                            guestCount[j] = count - countAtLatestClockwise + countAtFirstClockwise/2;
                        }
                    }
                    currentSet = i;
                    count = 0;
                    countAtFirstClockwise = 0;
                    countAtLatestClockwise = 0;
                }
                count += 1;
            }
        }
        
        int firstClockwise = 0;
        for (; firstClockwise <= clockwise.length; firstClockwise++) {
            if (firstClockwise == clockwise.length) {
                break;
            }
            if (clockwise[firstClockwise].size() > 0) {
                break;
            }
        }
        if (firstCounterClockwise > -1) {
            int count = 1;
            int currentSet = 0;
            int countAtFirstCounterClockwise = 0;
            int countAtLatestCounterClockwise = 0;
            for (int i = 1; i <= clockwise.length; i++) {
                if (counterclockwise[(clockwise.length + firstClockwise - i) % clockwise.length].size() > 0) {
                    if (countAtLatestCounterClockwise == 0) {
                        countAtFirstCounterClockwise = count + 1;
                    }
                    countAtLatestCounterClockwise = count;
                }
                if (clockwise[(clockwise.length + firstClockwise - i) % clockwise.length].size() > 0) {
                    for (Integer j : clockwise[(clockwise.length + firstClockwise - currentSet) % clockwise.length]) {
                        // System.out.println(j + " = " + count + " (count)");          // Debug
                        if (firstClockwise - i != 0) {
                            if (guestCount[j] > count - countAtLatestCounterClockwise + countAtFirstCounterClockwise/2) {
                                guestCount[j] = count - countAtLatestCounterClockwise + countAtFirstCounterClockwise/2;
                            }
                        } else {
                            if (guestCount[j] > countAtFirstCounterClockwise/2) {
                                guestCount[j] = countAtFirstCounterClockwise/2;
                            }
                        }
                    }
                    currentSet = i;
                    count = 0;
                    countAtFirstCounterClockwise = 0;
                    countAtLatestCounterClockwise = 0;
                }
                count += 1;
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner input = determineInput();
        int numberOfCases = input.nextInt();
        
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < numberOfCases; i++) {
                       
            int numberOfConsulates = input.nextInt();
            int numberOfGuests = input.nextInt();
            int numberOfMinutes = input.nextInt();
            
            ArrayList<Integer>[] clockwise = new ArrayList[numberOfConsulates];
            ArrayList<Integer>[] counterclockwise = new ArrayList[numberOfConsulates];
            int[] guestCount = new int[numberOfGuests];
            
            for (int j = 0; j < clockwise.length; j++) {
                clockwise[j] = new ArrayList<>();
                counterclockwise[j] = new ArrayList<>();
            }
            
            for (int j = 0; j < numberOfGuests; j++) {
                int start = input.nextInt();
                String direction = input.next();
                if (direction.charAt(0) == 'C') {
                    clockwise[(start + (numberOfMinutes % numberOfConsulates) - 1) % numberOfConsulates].add(j);
                } else {
                    counterclockwise[(start + numberOfConsulates - (numberOfMinutes % numberOfConsulates) - 1) % numberOfConsulates].add(j);
                }
                guestCount[j] = numberOfMinutes + 1 < numberOfConsulates ? numberOfMinutes + 1 : numberOfConsulates;
            }
            
            countGuests(clockwise, counterclockwise, guestCount);
            
            output.append("Case #");
            output.append(i+1);
            output.append(": ");
            for (int guest : guestCount) {
                output.append(guest);
                output.append(' ');
            }
            output.append('\n');
        }
        System.out.print(output.toString().trim());
    }
    
}