package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
internal actual fun ChatBackground() {
    val gradientColors = listOf(Color.Gray, Color.LightGray)
    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(gradientColors)
            )
    )
}