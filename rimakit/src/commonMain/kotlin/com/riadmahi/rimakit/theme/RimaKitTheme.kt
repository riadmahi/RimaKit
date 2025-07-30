package com.riadmahi.rimakit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val RimaPrimaryLight = Color(0xFF000000)
val RimaBackgroundLight = Color(0xFFFFFFFF)
val RimaSurfaceLight = Color(0xFFF7F7F7)
val RimaOutlineLight = Color(0xFFE0E0E0)
val RimaHighlightLight = Color(0xFFF4F6F8)

val RimaPrimaryDark = Color(0xFFFFFFFF)
val RimaBackgroundDark = Color(0xFF121212)
val RimaSurfaceDark = Color(0xFF1E1E1E)
val RimaOutlineDark = Color(0xFF333333)
val RimaHighlightDark = Color(0xFF2A2A2A)

private val LightColors = lightColorScheme(
    primary = RimaPrimaryLight,
    background = RimaBackgroundLight,
    surface = RimaSurfaceLight,
    outline = RimaOutlineLight,
)

private val DarkColors = darkColorScheme(
    primary = RimaPrimaryDark,
    background = RimaBackgroundDark,
    surface = RimaSurfaceDark,
    outline = RimaOutlineDark,
)

@Composable
fun RimaKitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = brSonomaTypography(),
        content = content
    )
}