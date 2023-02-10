package com.elnaur.sudoku;

/**
 * A generator to generate a complete Sudoku grid and then remove values so that it can be solved.
 * Based off of The ANZAC's 0.018s algorithm to generate a valid Sudoku grid. Found at
 * <a href="https://www.codeproject.com/Articles/23206/Sudoku-Algorithm-Generates-a-Valid-Sudoku-in-0-018">...</a>
 */
public class SudokuGenerator {

    final private int totalCells = 81;
    final private int sideLength = 10;  // Side length is 10, not 9 to allow for easy indexing
    // Index 0 is used to indicate if array is empty or not.
    private final Square[] squares;
    private final int[][] completeSudokuGrid;
    private final int[][] incompleteSudokuGrid;

    /**
     * 2D array that holds possible values for each cell. A true value indicates that
     */
    private final int[][] available;

    public SudokuGenerator() {
        squares = new Square[totalCells];    // Leave uninitialised for now
        available = new int[totalCells][sideLength];

        completeSudokuGrid = generateComplete();
        incompleteSudokuGrid = generateClues();
    }

    private int[][] generateComplete() {
        int count = 0;

        for (int i = 0; i < totalCells; i++) {
            resetSquare(available[i]);
        }

        while (count < totalCells) {
            if (available[count][0] > 0) {  // If there are still available numbers
                int num = getRandomAvailable(available[count]);
                Square square = new Square(count, num, sideLength - 1); // Create tentative square to insert

                // Check if proposed number conflicts
                if (!conflicts(square)) {
                    squares[count] = square;    // It works so add the value to available numbers for that square
                    remove(count, num);         // remove it from its individual list
                    count++;

                } else {
                    remove(count, num); // number conflicts so remove it
                }

            } else {    // If no available numbers, backtrack
                resetSquare(available[count]);    // Reset current square by resetting its available values
                squares[count - 1] = null;
                count--;        // Backtrack to try different number in previous square
            }
        }

        int c = 0;
        int[][] sudokuGrid = new int[sideLength - 1][sideLength - 1];
        for (int i = 0; i < sideLength - 1; i++) {
            for (int j = 0; j < sideLength - 1; j++) {
                sudokuGrid[i][j] = squares[c].getValue();   // Insert the valid value into its space in the sudoku grid
                c++;
            }
        }

        return sudokuGrid;
    }

    private void remove(int index, int num) {
        available[index][num] = 0;  // number conflicts so remove it
        available[index][0]--;
    }

    /**
     * Short helper method that sets the first index to max about of available
     * numbers and resets other numbers as available
     * @param arr   The array holding available numbers to be reset
     */
    private void resetSquare(int[] arr) {
        arr[0] = 9;    // First index has count of available values
        for (int j = 1; j < sideLength; j++) {
            arr[j] = j;
        }
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
    private int getRandomAvailable(int[] available) {
        int num;
        int[] nums = new int[sideLength - 1];  // Just need 9 indexes
        int c = 0;
        double r;

        for (int i = 1; i < sideLength; i++) {
            if (available[i] != 0) {
                nums[c] = i;
                c++;
            }
        }

        r = Math.random();
        num = nums[(int) Math.round(r * (c - 1))];

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
            if (square != null) {
                if ((square.getAcross() != 0 && square.getAcross() == testSquare.getAcross()) ||
                        (square.getDown() != 0 && square.getDown() == testSquare.getDown()) ||
                        (square.getRegion() != 0 && square.getRegion() == testSquare.getRegion())) {

                    if (square.getValue() == testSquare.getValue()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
