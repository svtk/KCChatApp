package ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val indigoMain = Color(0xff3949ab)
val indigoLight = Color(0xffaab6fe)
val orangeDark = Color(0xffc75b39)

private val ColorPalette = lightColors(
    primary = indigoMain,
    primaryVariant = indigoLight,
    secondary = orangeDark,
    secondaryVariant = orangeDark,
    background = Color.White,
    onSecondary = Color.White,
    /* Other default colors to override
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
internal fun ChatAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = ColorPalette,
        content = content
    )
}