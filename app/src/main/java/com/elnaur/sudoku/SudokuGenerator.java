package com.elnaur.sudoku;


import java.util.Arrays;

/**
 * A generator to generate a complete Sudoku grid and then remove values so that it can be solved.
 * Based off of The ANZAC's 0.018s algorithm to generate a valid Sudoku grid. Found at
 * <a href="https://www.codeproject.com/Articles/23206/Sudoku-Algorithm-Generates-a-Valid-Sudoku-in-0-018">...</a>
 */
public class SudokuGenerator {

    final private int totalCells = 81;
    final private int sideLength = 10;  // Sidelength is 10, not 9 to allow for easy indexing
                                        // Index 0 is used to indicate if array is empty or not.
    private final Square[] squares;
    private final int[][] completeSudokuGrid;
    private final int[][] incompleteSudokuGrid;

    /**
     * 2D array that holds possible values for each cell. A true value indicates that
     */
    private final boolean[][] available;

    public SudokuGenerator() {
        squares = new Square[totalCells];    // Leave uninitialised for now
        available = new boolean[totalCells][sideLength];

        completeSudokuGrid = generateComplete();
        incompleteSudokuGrid = generateClues();
    }

    private int[][] generateComplete() {
        int count = 0;

        for (int i = 0; i < totalCells; i++) {
            Arrays.fill(available[i], true);    // Index 0 is filled too, to indicate array is not empty
        }

        while (count < totalCells) {
            if (!available[count][0]) {
                int num = getRandomAvailable(available[count]);
                count++;

                Square square = new Square(count, num, sideLength); // Create tentative square to insert

                if (!conflicts(square)) { // Check if proposed number conflicts
                    // It works so add the value to avilable numbers for that square
                    available[count][num] = false;  // remove it from its individual list
                    count++;
                } else {
                    available[count][num] = false;  // number conflicts so remove it
                }
            } else {
                Arrays.fill(available[count], true);    // Reset current square by resetting its available values
                squares[count - 1] = null;
                count--;        // Backtrack to try different number in previous square
            }
        }

        int c = 0;
        int[][] sudokuGrid = new int[9][9];
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                sudokuGrid[i][j] = squares[c].getValue();   // Insert the valid value into its space in the sudoku grid
                c++;
            }
        }

        return sudokuGrid;
    }

    private int[][] generateClues() {
        int[][] sudokuGrid = new int[9][9];

        return sudokuGrid;
    }

    public int[][] getCompletedGrid() {
        return completeSudokuGrid;
    }

    public int[][] getIncompleteSudokuGrid() {
        return incompleteSudokuGrid;
    }

    /**
     * Helper method that gets a random number from the remaining available numbers to try
     * @param available Cell's available numbers
     * @return  A random available number
     */
    private int getRandomAvailable(boolean[] available) {
        int num;
        int[] nums = new int[sideLength - 1];  // Just need 9 indexes
        int c = 0;
        double r;
        
        for (int i = 1; i < sideLength; i++) {
            if (available[i]) {
                nums[c] = i;
                c++;
            }
        }

        r = Math.random();
        num = nums[(int) Math.round(r * c)];

        return num;
    }

    /**
     * Helper method that checks if there are any conflicts with the tentative square value.
     * It checks for row, column and region conflicts.
     * @param testSquare    The tentative square to be inserted.
     * @return  True if there are conflicts, false otherwise.
     */
    private boolean conflicts(Square testSquare) {
        for (Square square : squares) {
            if ((square.getAcross() > 0 && square.getAcross() == testSquare.getAcross()) ||
                    (square.getDown() > 0 && square.getDown() == testSquare.getDown()) ||
                    (square.getRegion() > 0 && square.getRegion() == testSquare.getRegion())) {
                if (square.getValue() == testSquare.getValue()) {
                    return true;
                }

            }
        }

        return false;
    }

}
