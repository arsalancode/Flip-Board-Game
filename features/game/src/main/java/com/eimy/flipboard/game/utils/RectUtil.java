package com.eimy.flipboard.game.utils;

// Java program to find largest rectangle with all 1s
// in a binary matrix
// https://stackoverflow.com/questions/7245/puzzle-find-largest-rectangle-maximal-rectangle-problem

import android.graphics.Point;

import com.eimy.flipboard.game.model.Cell;
import com.eimy.flipboard.game.model.CellState;
import com.eimy.flipboard.game.model.RectResult;

import org.jetbrains.annotations.NotNull;

import java.util.Stack;

public class RectUtil {

    private static int[] updateCache(int[] cache, int[] matrixRow, int MaxX) {
        for (int m = 0; m < MaxX; m++) {
            if (matrixRow[m] == 0) {
                cache[m] = 0;
            } else {
                cache[m]++;
            }
        }
        return cache;
    }

    private static RectResult findRect(int[][] matrix) {
        Point best_ll = new Point(0, 0);
        Point best_ur = new Point(-1, -1);
        int best_area = 0;

        final int MaxX = matrix[0].length;
        final int MaxY = matrix.length;

        Stack<Point> stack = new Stack<Point>();
        int[] cache = new int[MaxX + 1];

        for (int m = 0; m != MaxX + 1; m++) {
            cache[m] = 0;
        }

        for (int n = 0; n != MaxY; n++) {
            int openWidth = 0;
            cache = updateCache(cache, matrix[n], MaxX);
            for (int m = 0; m != MaxX + 1; m++) {
                if (cache[m] > openWidth) {
                    stack.push(new Point(m, openWidth));
                    openWidth = cache[m];
                } else if (cache[m] < openWidth) {
                    int area;
                    Point p;
                    do {
                        p = stack.pop();
                        area = openWidth * (m - p.x);
                        if (area > best_area) {
                            best_area = area;
                            best_ll.x = p.x;
                            best_ll.y = n;
                            best_ur.x = m - 1;
                            best_ur.y = n - openWidth + 1;
                        }
                        openWidth = p.y;
                    } while (cache[m] < openWidth);
                    openWidth = cache[m];
                    if (openWidth != 0) {
                        stack.push(p);
                    }
                }
            }
        }

//        System.out.printf("The maximal rectangle has area %d.\n", best_area);
//        System.out.printf("Location: [col=%d, row=%d] to [col=%d, row=%d]\n", best_ll.x + 1, best_ll.y + 1,
//                best_ur.x + 1, best_ur.y + 1);

        return new RectResult(best_area,
                Math.min(best_ll.y, best_ur.y),
                Math.min(best_ll.x, best_ur.x),
                Math.max(best_ll.y, best_ur.y),
                Math.max(best_ll.x, best_ur.x)
        );
    }

    /**
     *
     * @param matrix game matrix
     * @return Returns an object containing size and start / end point of rectangle
     */
    public static RectResult getRectangle(@NotNull Cell[][] matrix){

        if (matrix.length <= 0)
            return new RectResult(0, 0, 0, 0, 0);

        int[][] array = convertToIntArray(matrix);
        return findRect(array);
    }

    private static int[][] convertToIntArray( Cell[][] matrix ){
        int[][] array = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++ ){
            for (int j = 0; j < matrix[0].length; j++){
                array[i][j] = matrix[i][j].getState() == CellState.OFF ? 0 : 1;
            }
        }
        return array;
    }

}
