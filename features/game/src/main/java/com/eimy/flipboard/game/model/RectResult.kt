package com.eimy.flipboard.game.model

data class RectResult(
    var size: Int,
    val minI: Int,
    val minJ: Int,
    val maxI: Int,
    val maxJ: Int,
){
    override fun toString(): String {
        return "RectResult(size=$size, minI=$minI, minJ=$minJ, maxI=$maxI, maxJ=$maxJ)"
    }
}
