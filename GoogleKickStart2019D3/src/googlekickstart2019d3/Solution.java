/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlekickstart2019d3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew Dimmer
 * Solution to the Google Kick Start 2019 Round D Food Stalls Problem
 * https://codingcompetitions.withgoogle.com/kickstart/round/0000000000051061/0000000000161476
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
    
    private static int getCost(int[] spots, int[] costs, int numberOfStalls) {
        int minCost = Integer.MAX_VALUE;
        for (int j = 0; j < spots.length; j++) {
            int[] modCosts = new int[spots.length - 1];
            for (int i = 0; i < j; i++) {
                modCosts[i] = costs[i] + Math.abs(spots[j] - spots[i]);
            }
            for (int i = j + 1; i < spots.length; i++) {
                modCosts[i - 1] = costs[i] + Math.abs(spots[j] - spots[i]);
            }
            Arrays.sort(modCosts);
            int currentCost = costs[j];
            for (int i = 0; i < numberOfStalls; i++) {
                currentCost += modCosts[i];
            }
            if (minCost > currentCost) {
                minCost = currentCost;
            }
        }
        return minCost;
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
                       
            int numberOfStalls = input.nextInt();
            int numberOfSpots = input.nextInt();
            
            int[] spots = new int[numberOfSpots];
            for (int j = 0; j < spots.length; j++) {
                spots[j] = input.nextInt();
            }
            
            int[] costs = new int[numberOfSpots];
            for (int j = 0; j < spots.length; j++) {
                costs[j] = input.nextInt();
            }
            
            output.append("Case #");
            output.append(i+1);
            output.append(": ");
            output.append(getCost(spots, costs, numberOfStalls));
            output.append('\n');
        }
        System.out.print(output.toString().trim());
    }
    
}