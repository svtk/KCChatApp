package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
internal actual fun ChatBackground() {
//    val gradientColors = listOf(Color.Gray, Color.LightGray)
//    Box(
//        modifier = Modifier.fillMaxSize()
//            .background(
//                brush = Brush.horizontalGradient(gradientColors)
//            )
//    )
    Image(
        painter = painterResource("bg.png"),
        contentDescription = "",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxWidth(),
    )
}