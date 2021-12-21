package com.eimy.flipboard.game.uimodels

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.eimy.flipboard.contact.R
import com.eimy.flipboard.core.lists.DataBoundModel
import com.eimy.flipboard.game.model.Cell
import com.eimy.flipboard.game.model.CellState

class CellUiModel(var cell: Cell, private val onClick: (() -> Unit)?) :
    DataBoundModel(R.layout.cell) {

    init {
        Log.i("CellUiModel", cell.toString())
    }

    val cellState = MutableLiveData<Int>().apply { postValue(cell.state.ordinal) }

    fun onClick(clickedView: View) {
        cell.toggleState()
        cellState.value = cell.state.ordinal

//        Log.i("onClick", cell.toString())

        onClick?.invoke()
    }

}
