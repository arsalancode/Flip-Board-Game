package com.eimy.flipboard.game.model

import android.graphics.Color
import com.eimy.flipboard.contact.R

data class Cell(
    val row: Int,
    val col: Int,
    var state: CellState = CellState.OFF,
    var color: Int = R.color.off
){

    fun toggleState(){
        state = if ( state == CellState.OFF) CellState.ON else CellState.OFF
    }

    fun getAsSingleIndex() : Int {
        return row * row + col
    }

}
