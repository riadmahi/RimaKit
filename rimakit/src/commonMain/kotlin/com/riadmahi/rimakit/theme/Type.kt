package com.riadmahi.rimakit.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import rimakit.rimakit.generated.resources.Res
import androidx.compose.material3.Typography
import rimakit.rimakit.generated.resources.brsonoma_bold
import rimakit.rimakit.generated.resources.brsonoma_medium
import rimakit.rimakit.generated.resources.brsonoma_regular
import rimakit.rimakit.generated.resources.brsonoma_semibold

@Composable
fun brSonomaFontFamily() = FontFamily(
    Font(Res.font.brsonoma_regular, FontWeight.Normal),
    Font(Res.font.brsonoma_bold, FontWeight.Bold),
    Font(Res.font.brsonoma_semibold, FontWeight.SemiBold),
    Font(Res.font.brsonoma_medium, FontWeight.Medium),
)

@Composable
fun brSonomaTypography(): Typography {
    val fontFamily = brSonomaFontFamily()

    return Typography().run {
        Typography(
            displayLarge = displayLarge.copy(fontFamily = fontFamily),
            displayMedium = displayMedium.copy(fontFamily = fontFamily),
            displaySmall = displaySmall.copy(fontFamily = fontFamily),

            headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
            headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
            headlineSmall = headlineSmall.copy(fontFamily = fontFamily),

            titleLarge = titleLarge.copy(fontFamily = fontFamily),
            titleMedium = titleMedium.copy(fontFamily = fontFamily),
            titleSmall = titleSmall.copy(fontFamily = fontFamily),

            bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
            bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
            bodySmall = bodySmall.copy(fontFamily = fontFamily),

            labelLarge = labelLarge.copy(fontFamily = fontFamily),
            labelMedium = labelMedium.copy(fontFamily = fontFamily),
            labelSmall = labelSmall.copy(fontFamily = fontFamily),
        )
    }
}