package com.eimy.flipboard.game.utils

object Utils {


    /**
     * Convert 2D array index To 1D array/list index
     */

    fun convert2dTo1dIndex( rows: Int, row: Int, col: Int ) : Int{
        return rows * row + col
    }

}