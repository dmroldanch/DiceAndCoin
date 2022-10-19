package com.aptivist.diceandcoin.models

import androidx.compose.ui.graphics.Color

data class ResultItem(
    val name:String,
    val id:String,
    val backgroundColor: Color,
    val url:String,
    var result:String,
)
