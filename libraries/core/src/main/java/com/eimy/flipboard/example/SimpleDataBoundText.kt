package com.eimy.flipboard.example

import androidx.lifecycle.MutableLiveData
import com.eimy.flipboard.core.R
import com.eimy.flipboard.core.lists.DataBoundModel

class SimpleDataBoundText : DataBoundModel(R.layout.data_bound_text) {
    val text = MutableLiveData<String>()
}