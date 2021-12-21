package com.eimy.flipboard.game

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eimy.flipboard.game.model.Cell
import com.eimy.flipboard.game.model.Game
import com.eimy.flipboard.game.uimodels.CellUiModel
import kotlinx.coroutines.launch
import android.text.Spannable

import android.text.style.ForegroundColorSpan

import android.text.SpannableString
import android.util.Log
import android.view.View
import com.eimy.flipboard.contact.R
import com.eimy.flipboard.game.model.CellState
import com.eimy.flipboard.game.model.RectResult
import com.eimy.flipboard.game.utils.RectUtil
import com.eimy.flipboard.game.utils.Utils


@RequiresApi(Build.VERSION_CODES.N)
class GameViewModel(
    private val rows : Int,
    private val columns : Int
) : ViewModel() {

    private val TAG = "GameViewModel"

    lateinit var gameBoard: Game
    var rectangleSize: Int = 0
    lateinit var boardList: List<CellUiModel>

    val txtRectangleSize = MutableLiveData<Spannable>()
    val cellUiList = MutableLiveData<List<CellUiModel>>()

    val showDialog = MutableLiveData<Boolean>().apply {
        value = false
    }

    init {

        viewModelScope.launch{
            initBoard()
        }

    }

    fun initBoard(){
        rectangleSize = 0
        txtRectangleSize.postValue(getText(rectangleSize))

        gameBoard = Game(getGameBoard())
        boardList = getBoardCells()
        cellUiList.postValue(boardList)
    }

    private fun getBoardCells(): List<CellUiModel> {
        val list = mutableListOf<CellUiModel>()
        for (col in gameBoard.matrix) {
            for (cell in col) {

                val cellUiModel = CellUiModel(cell) {
                    resetUi()
                    calculateSize(cell)
                }

                list.add(cellUiModel)
            }
        }

        return list
    }

    private fun resetUi() {

        // Reset red rect
        boardList.forEach {
            if (it.cell.state == CellState.RED) {
                Log.i(TAG, "resetUi: ${it.cell}")
                it.cell.state = CellState.ON
                it.cellState.value = it.cell.state.ordinal
            }
        }
    }

    private fun calculateSize(cell: Cell) {

        val result = RectUtil.getRectangle(gameBoard.matrix)
        Log.i(TAG, "Result: $result")

        rectangleSize = result.size

        // Make new Rect Selection
        updateRectangle(result, R.color.red, CellState.RED)

        // Update Rect Size
        txtRectangleSize.postValue(getText(rectangleSize))
    }

    private fun updateRectangle(result: RectResult, color: Int, state: CellState) {

        for (i in result.minI..result.maxI) {
            for (j in result.minJ..result.maxJ) {
                gameBoard.matrix[i][j].color = color
                gameBoard.matrix[i][j].state = state

                val index = Utils.convert2dTo1dIndex(rows, i, j)
                boardList[index].cellState.value = state.ordinal
            }
        }
    }

    private fun getText(rectangleSize: Int): Spannable {
        val txt = "Biggest Rectangle: "
        val word: Spannable = SpannableString(txt + rectangleSize)

        word.setSpan(
            ForegroundColorSpan(Color.BLACK),
            0,
            txt.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        word.setSpan(
            ForegroundColorSpan(Color.RED),
            txt.length,
            word.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return word
    }

    private fun getGameBoard(): Array<Array<Cell>> {
        val gameBoard = Array(rows) { row ->
            Array(columns) { col ->
                Cell(row, col)
            }
        }

        return gameBoard;
    }

    fun onResetClick(clickedView: View) {
        Log.i(TAG, "Reset Board")
        //initBoard()
        showDialog.postValue(true)
    }

}