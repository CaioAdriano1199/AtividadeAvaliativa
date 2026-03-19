package model

data class Tip(
    val ammount: Double = 0.0,
    val custom: Int = 18,
    val tip15: Double = 0.0,
    val total15: Double = 0.0,
    val tipCustom: Double = 0.0,
    val totalCustom: Double = 0.0
)