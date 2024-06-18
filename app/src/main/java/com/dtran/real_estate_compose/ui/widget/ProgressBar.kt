package com.dtran.real_estate_compose.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun AppProgressBar(loading: Boolean) {
    if (loading) Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) { }, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            strokeCap = StrokeCap.Round
        )
    }
}