package com.aptivist.diceandcoin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.aptivist.diceandcoin.models.ResultItem
import kotlinx.coroutines.delay

class MainViewModel : ViewModel() {

    var isCurrentlyDragging by mutableStateOf(false)
        private set

    var isResultShowing by mutableStateOf(false)
        private set

    var isCurrenItem by mutableStateOf("🎲")
        private set

    var items by mutableStateOf(emptyList<ResultItem>())
        private set

    var addedResult = mutableStateListOf<ResultItem>()
        private set

    var isNewElement by mutableStateOf(false)
        private set

    var isNewFristTime by mutableStateOf(false)
        private set

    init {
        items = listOf(
            ResultItem("🎲","1", Color.Transparent,"dice.json",""),
            ResultItem("🪙","2", Color.Transparent,"coin.json",""),
        )
    }

    fun startDragging(){
        isCurrentlyDragging = true
        isNewElement = true
        isResultShowing = false
    }

    suspend fun stopDragging(){
        isNewFristTime = true
        isCurrentlyDragging = false
        delay(2000)
        isResultShowing = true

    }

    fun stopCurentItem(person : String){
        if(person.contains("dice"))
            isCurrenItem = "🎲"
        else
            isCurrenItem = "🪙"
    }


    fun addResult(resultItem: ResultItem,currentNumber : Int){
        if (resultItem.name == "🎲")
            resultItem.result = currentNumber.toString()
        else {
            if (currentNumber < 3)
                resultItem.result = "Heads"
            else
                resultItem.result = "Tails"
        }
        isNewElement = !isNewElement
        addedResult.add(resultItem)
    }

}