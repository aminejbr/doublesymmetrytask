package com.aminejbr.doublesymmetrytask.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.aminejbr.doublesymmetrytask.R

@Composable
fun LoadingSpinner(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing)
        )
    )
    Box(
        modifier = modifier.graphicsLayer {
            rotationZ = angle
        }
    ) {
        Image(
            painter = painterResource(id = R.drawable.loading_indicator),
            contentDescription = "loading_indicator")
    }
}