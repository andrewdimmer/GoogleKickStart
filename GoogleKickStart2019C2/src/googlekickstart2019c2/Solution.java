/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlekickstart2019c2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew Dimmer
 * Solution to the Google Kick Start 2019 Round C Circuit Board Problem
 * https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050ff2/0000000000150aae
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
    
    private static void getGoodRanges(ArrayList<int[]> array, int[]thickness, int maxVar) {
        int rangeStart = 0, rangeStop = 0, rangeMin = thickness[0], rangeMax = thickness[0], indexMin = 0, indexMax = 0;
        for (int i = 1; i < thickness.length; i++) {
            if (thickness[i] >= rangeMin && thickness[i] <= rangeMax) {          // The range min/max did not change, so by definition still within maxVar
                rangeStop++;
                if (rangeMin == thickness[i]) {          // If the current index is equal to the min, set the indexMin to the right most min index
                    indexMin = i;
                } else if (rangeMax == thickness[i]) {          // If the current index is equal to the max, set the indexMax to the right most max index
                    indexMax = i;
                }
            } else {
                if (thickness[i] < rangeMin) {          // The current index is less than the min
                    rangeMin = thickness[i];          // Adjust the rangeMin to the new minimum
                    indexMin = i;          // Set the new indexMin
                    if (rangeMax - rangeMin > maxVar) {         // New min sets the range outside of tolarance
                        array.add(new int[]{rangeStart, rangeStop});          // Add the range that ended before the new value; that is valid
                        rangeStop++;
                        boolean newStartSet = false;
                        while (!newStartSet) {          // If the range has the new min, it cannot have the old max
                            rangeStart = indexMax+1;          // Therefore, the first possible start value of the new range is one more than the old max
                            rangeMax = thickness[rangeStart];          // Set the new max to the first value in the range
                            indexMax = rangeStart;
                            int currentIndex = rangeStart;
                            while(currentIndex <= rangeStop) {           // Check that the first value in the new range is actually the max (ex. [0,1], 0 is the first value but not the max
                                if (thickness[currentIndex] > rangeMax) {          // The current value is greater than the recorded max
                                    rangeMax = thickness[currentIndex];          // Set the recorded max equal to the current value
                                    indexMax = currentIndex;
                                    if (rangeMax - rangeMin > maxVar) {        // If the new max is still outside tolarance, move the start point again.
                                        break;
                                    }
                                }
                                currentIndex++;
                            }
                            if (rangeMax - rangeMin <= maxVar) {          // New start point has a maximum that is within tolarance
                                newStartSet = true;          // Therefore, stop setting the startpoint, and resume moving the end point right
                            }
                        }
                    } else {          // The new min is within tolarance
                        rangeStop++;      // Move the endpoint and keep searching for the right endpoint
                    }
                } else {          // By defintion, thickness[i] > rangeMax if either the max or min is different, and the min is not different
                    rangeMax = thickness[i];          // Adjust the rangeMax to the new maximum
                    indexMax = i;          // Set the new indexMax
                    if (rangeMax - rangeMin > maxVar) {         // New max sets the range outside of tolarance
                        array.add(new int[]{rangeStart, rangeStop});          // Add the range that ended before the new value; that is valid
                        rangeStop++;
                        boolean newStartSet = false;
                        while (!newStartSet) {          // If the range has the new max, it cannot have the old min
                            rangeStart = indexMin+1;          // Therefore, the first possible start value of the new range is one more than the old min
                            rangeMin = thickness[rangeStart];          // Set the new min to the first value in the range
                            indexMin = rangeStart;
                            int currentIndex = rangeStart;
                            while(currentIndex <= rangeStop) {           // Check that the first value in the new range is actually the min (ex. [1,0], 1 is the first value but not the min
                                if (thickness[currentIndex] < rangeMin) {          // The current value is less than the recorded min
                                    rangeMin = thickness[currentIndex];          // Set the recorded min equal to the current value
                                    indexMin = currentIndex;
                                    if (rangeMax - rangeMin > maxVar) {        // If the new min is still outside tolarance, move the start point again.
                                        break;
                                    }
                                }
                                currentIndex++;
                            }
                            if (rangeMax - rangeMin <= maxVar) {          // New start point has a maximum that is within tolarance
                                newStartSet = true;          // Therefore, stop setting the startpoint, and resume moving the end point right
                            }
                        }
                    } else {          // The new max is within tolarance
                        rangeStop++;      // Move the endpoint and keep searching for the right endpoint
                    }
                }
            }
        }
        array.add(new int[]{rangeStart, rangeStop});          // Add the last range to the array; otherwise never saves the last range (which is valid)!
    }
    
    private static int getMaxRectangleSize(ArrayList<int[]>[] goodRanges, int startCol, int stopCol, int startRow, int stopRow, int currentRow, int minRangeWidth) {
        int maxRectangle = (currentRow - startRow) * (stopCol - startCol + 1);
        if (currentRow == stopRow) {          // Out of rows to check
            return (stopRow - startRow) * (stopCol - startCol + 1);
        }
        for (int i = 0; i < goodRanges[currentRow].size(); i++) {
            if ((goodRanges[currentRow].get(i)[1] >= startCol) || (goodRanges[currentRow].get(i)[0] <= stopCol)) {          // Overlap
                int overlapStart = startCol, overlapStop = stopCol;
                if ((goodRanges[currentRow].get(i)[0] > startCol)) {
                    overlapStart = goodRanges[currentRow].get(i)[0];
                }
                if (goodRanges[currentRow].get(i)[1] < stopCol) {
                    overlapStop = goodRanges[currentRow].get(i)[1];
                }
                if (overlapStop - overlapStart >= minRangeWidth - 1) {
                    int localMax = getMaxRectangleSize(goodRanges, overlapStart, overlapStop, startRow, stopRow, currentRow + 1, minRangeWidth);
                    if (localMax > maxRectangle) {
                        maxRectangle = localMax;
                    }
                }
            }
        }
        return maxRectangle;
    }
    
    private static void listGoodRanges(ArrayList<int[]> array, int row) {
        String output = "Good Ranges in row #" + row + ": [" + array.get(0)[0] + ", " + array.get(0)[1] + "]";
        for (int i = 1; i < array.size(); i++) {
            output += ", [" + array.get(i)[0] + ", " + array.get(i)[1] + "]";
        }
        System.out.println(output);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner input = determineInput();
        int numberOfCases = input.nextInt();
        
        for (int i = 0; i < numberOfCases; i++) {
                       
            int r = input.nextInt();
            int c = input.nextInt();
            int k = input.nextInt();
            
            int[][] thickness = new int[r][c];
            for (int sr = 0; sr < r; sr++) {
                for (int sc = 0; sc < c; sc++) {
                    thickness[sr][sc] = input.nextInt();
                }
            }
            
            ArrayList<int[]>[] goodRanges = new ArrayList[r];
            for (int row = 0; row < r; row++) {
                goodRanges[row] = new ArrayList<>();
                getGoodRanges(goodRanges[row], thickness[row], k);
                //listGoodRanges(goodRanges[row], row);          // Debug
            }
            
            int maxRectangle = getMaxRectangleSize(goodRanges, 0, c-1, 0, r, 0, 1);
            for (int row = 1; row < r; row++) {
                int localMax = getMaxRectangleSize(goodRanges, 0, c-1, row, r, 0, 1);
                if (localMax > maxRectangle) {
                    maxRectangle = localMax;
                }
            }
            
            System.out.println("Case #" + (i+1) + ": " + maxRectangle);
        }
    }
    
}