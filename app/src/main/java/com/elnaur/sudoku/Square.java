package com.elnaur.sudoku;

import java.util.ArrayList;
import java.util.Arrays;

public class Square {

    private final int across;
    private final int down;
    private final int region;
    private final int value;
    private final int index;

    private final int size;

    public Square(int indx, int val, int sz) {
        index = indx;
        value = val;
        size = sz;

        indx++;
        across = getAcrossFromNum(indx);
        down = getDownFromNum(indx);
        region = getRegionFromNum();
    }

    public int getAcross() {
        return across;
    }

    public int getDown() {
        return down;
    }

    public int getRegion() {
        return region;
    }

    public int getValue() {
        return value;
    }

    private int getAcrossFromNum(int indx) {
        int k;
        k = indx % size;
        if (k == 0) {
            return size;
        } else {
            return k;
        }
    }

    private int getDownFromNum(int indx) {
        int k;
        if (across == size) {
            k = indx/across;
        } else {
            k = indx/across + 1;
        }

        return k;

    }

    private int getRegionFromNum() {
        // NB NOTE THIS CODE ONLY WORKS FOR 9 BECAUSE IT'S TOO INEFFIENT TO TRY AND MAKE IT WORK
        // FOR OTHER NUMBERS. IF NEEDED THIS IS THE ONLY PART THAT NEEDS TO BE ADJUSTED TO ALLOW
        // SUDOKU GRIDS OF DIFFERENT SIZES.
        // TODO: Make code less gross

        if (1 <= across && across < 4 && 1 <= down && down < 4) {
            return 1;
        } else if (4 <= across && across < 7 && 1 <= down && down < 4) {
            return 2;
        } else if (7 <= across && across < 10 && 1 <= down && down < 4) {
            return 3;
        } else if (1 <= across && across < 4 && 4 <= down && down < 7) {
            return 4;
        } else if (4 <= across && across < 7 && 4 <= down && down < 7) {
            return 5;
        } else if (7 <= across && across < 10 && 4 <= down && down < 7) {
            return 6;
        } else if (1 <= across && across < 4 && 7 <= down && down < 10) {
            return 7;
        } else if (4 <= across && across < 7 && 7 <= down && down < 10) {
            return 8;
        } else {
            return 9;
        }
    }
}
