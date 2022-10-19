package com.aptivist.diceandcoin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.aptivist.diceandcoin.models.ResultItem


@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val random = (1..6).random()
    var currentNumber by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color(9, 9, 121)
                    )
                ),
            ),
        verticalArrangement = Arrangement.spacedBy(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        elementsCoinAndDice(mainViewModel = mainViewModel, screenWidth = screenWidth)

        DropItem<ResultItem>(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(8.dp)
        ) { isInBound, resultItem ->
            if (resultItem != null) {
                LaunchedEffect(key1 = resultItem) {
                    currentNumber = random
                    mainViewModel.addResult(resultItem,currentNumber)

                }
            }

            if (!mainViewModel.isCurrentlyDragging && !mainViewModel.isNewElement && mainViewModel.isNewFristTime) {
                tableAfterResult(mainViewModel, currentNumber)
            } else {
                tableBeforeResult()
            }
        }

        if (mainViewModel.addedResult.size > 0 && mainViewModel.isResultShowing) {
            resultView(mainViewModel)
        }
    }
}

@Composable
fun elementsCoinAndDice(mainViewModel: MainViewModel,screenWidth : Int){

    val retrySignal = rememberLottieRetrySignal()

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(10.dp)
                .background(color = Color.Transparent),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            mainViewModel.items.forEach { person ->
                DragTarget(
                    dataToDrop = person,
                    viewModel = mainViewModel
                ) {

                    Box(
                        modifier = Modifier
                            .size(Dp(screenWidth / 2f))
                            .height(200.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(person.backgroundColor),
                        contentAlignment = Alignment.Center,
                    ) {

                        val composition by rememberLottieComposition(
                            LottieCompositionSpec.Asset(
                                person.url
                            ), onRetry = { failCount, exception ->
                                retrySignal.awaitRetry()
                                // Continue retrying. Return false to stop trying.
                                true
                            })
                        val composition2 by rememberLottieComposition(
                            LottieCompositionSpec.Asset(
                                person.url
                            ), onRetry = { failCount, exception ->
                                retrySignal.awaitRetry()
                                // Continue retrying. Return false to stop trying.
                                true
                            })
                        val clipSpecifit = LottieClipSpec.Frame(
                            min = 0,
                            max = if (person.name == "🎲") 20 else 40
                            //max = if(person.name == "🎲") 20 else 40
                        )
                        Box(
                            modifier = Modifier.padding(30.dp)
                        ) {
                            if (!mainViewModel.isCurrentlyDragging) {
                                LottieAnimation(
                                    composition = composition,
                                    speed = 0.09F,
                                    isPlaying = true,
                                    restartOnPlay = true,
                                    iterations = 200,
                                    clipSpec = clipSpecifit
                                )
                            } else {
                                LottieAnimation(
                                    composition = composition2,
                                    speed = 4.0F,
                                    isPlaying = true,
                                    restartOnPlay = true,
                                    iterations = 200,
                                    clipSpec = clipSpecifit
                                )
                            }

                        }


                    }
                }
            }

        }
        if (mainViewModel.isCurrentlyDragging)
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black,
                                Color(3, 2, 26)
                            )
                        )
                    )
                    .fillMaxWidth()
                    .height(200.dp)
            ) {

            }
    }
}

@Composable
fun tableBeforeResult() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .border(
                1.dp,
                color = Color.Transparent,
                shape = RoundedCornerShape(0.dp)
            )
            .background(
                Color.Transparent,
                RoundedCornerShape(0.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.table),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 30.dp),
            contentDescription = "Table",
        )
    }
}


@Composable
fun tableAfterResult(mainViewModel: MainViewModel, currentNumber: Int) {

    val retrySignal = rememberLottieRetrySignal()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(100.dp)
            .border(
                0.dp,
                color = Color.Transparent,
                shape = RoundedCornerShape(0.dp)
            )
            .background(Color.Transparent, RoundedCornerShape(0.dp)),
        contentAlignment = Alignment.Center
    ) {


        Box {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 20.dp),
                painter = painterResource(R.drawable.table),
                contentDescription = "Table",
            )
            Box {

                val composition3 by rememberLottieComposition(
                    LottieCompositionSpec.Asset(
                        "explotion.json"
                    ), onRetry = { failCount, exception ->
                        retrySignal.awaitRetry()
                        // Continue retrying. Return false to stop trying.
                        true
                    })
                val compositionDice by rememberLottieComposition(
                    LottieCompositionSpec.Asset(
                        if (mainViewModel.isCurrenItem == "🪙") "coin.json" else "dice.json"
                    ), onRetry = { failCount, exception ->
                        retrySignal.awaitRetry()
                        true
                    })
                // 2 = 26 ,1 = 25, 3 - 33, 4 - 36, 5 - 43, 6 - 49
                println("clipSpecDice Person ${currentNumber}")


                val clipSpecDice = LottieClipSpec.Frame(
                    min = 0,
                    max = if (mainViewModel.isCurrenItem == "🎲") {
                        when (currentNumber) {
                            1 -> 22
                            else -> 23 + (currentNumber * 4)
                        }
                    } else {
                        if (currentNumber < 3) 10 else 30
                    }
                )

                if (mainViewModel.isNewFristTime) {

                    LottieAnimation(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .height(80.dp),
                        composition = compositionDice,
                        speed = if (mainViewModel.isCurrenItem == "🎲") 1F else 5F,
                        isPlaying = true,
                        restartOnPlay = false,
                        iterations = if (mainViewModel.isCurrenItem == "🎲") 1 else 5,
                        clipSpec = clipSpecDice
                    )

                    LottieAnimation(
                        modifier = Modifier.align(Alignment.Center),
                        composition = composition3,
                        speed = 0.8F,
                        isPlaying = true,
                        restartOnPlay = false,
                    )
                }
            }
        }


    }
}

@Composable
fun resultView(mainViewModel: MainViewModel) {
    Box {
        Column(
            modifier = Modifier
                .background(Color.Black.copy(0.5F))
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Results",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
            )
            mainViewModel.addedResult.let {
                Text(
                    text = "${it.last().name} - ${it.last().result}",
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 35.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }

    }


}