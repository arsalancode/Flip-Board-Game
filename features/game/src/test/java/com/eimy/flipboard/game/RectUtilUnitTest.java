package com.eimy.flipboard.game;

import static org.junit.Assert.assertEquals;

import com.eimy.flipboard.contact.R;
import com.eimy.flipboard.game.model.Cell;
import com.eimy.flipboard.game.model.CellState;
import com.eimy.flipboard.game.model.RectResult;
import com.eimy.flipboard.game.utils.RectUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit test written to test RectUtil functionality to find max Rect Size
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class RectUtilUnitTest {

    @Test
    public void reactSize_isWrong() {

        RectResult expectedResult = new RectResult(5, 0, 1, 0, 3);

        Cell[][] matrix = new Cell[5][5];
        for(int i = 0; i < matrix.length; i++ ){
            for ( int j = 0; j < matrix[0].length; j++){
                matrix[i][j] = new Cell(i, j, CellState.OFF, R.color.white);
            }
        }
        matrix[1][1].setState(CellState.ON);
        matrix[2][2].setState(CellState.ON);
        matrix[3][3].setState(CellState.ON);
        RectResult actualResult = RectUtil.getRectangle(matrix);

        assertEquals(expectedResult.getSize(), actualResult.getSize());
    }

    private void printArray(Cell[][] matrix){
        System.out.println("printArray");
        for (Cell[] cells : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(cells[j].getState() + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void reactCompleteRect_isWrong() {

        RectResult expectedResult = new RectResult(3, 0, 1, 0, 3);

        Cell[][] matrix = new Cell[5][5];
        for(int i = 0; i < matrix.length; i++ ){
            for ( int j = 0; j < matrix[0].length; j++){
                matrix[i][j] = new Cell(i, j, CellState.OFF, R.color.white);
            }
        }
        matrix[1][0].setState(CellState.ON);
        matrix[2][2].setState(CellState.ON);
        matrix[3][4].setState(CellState.ON);
        matrix[1][1].setState(CellState.ON);

        RectResult result = RectUtil.getRectangle(matrix);

        assertEquals(expectedResult, result);
    }
}