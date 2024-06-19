package com.dtran.real_estate_compose.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.dtran.real_estate_compose.R

//@Composable
//fun AppProgressBar(loading: Boolean) {
//    if (loading) Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .pointerInput(Unit) { }, contentAlignment = Alignment.Center
//    ) {
//        CircularProgressIndicator(
//            strokeCap = StrokeCap.Round
//        )
//    }
//}

@Composable
fun LottieProgressBar(loading: Boolean) {
    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) { }, contentAlignment = Alignment.Center
        ) {
            val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_loading))
            LottieAnimation(
                composition = composition.value,
                maintainOriginalImageBounds = true,
                contentScale = ContentScale.Fit,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}