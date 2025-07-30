package com.riadmahi.rimakit.components.animatednumber

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedNumberRandom(
    value: Double,
    diff: Double,
    modifier: Modifier = Modifier,
    currencySymbol: String = "$",
    positiveColor: Color = Color(0xFF10B981),
    negativeColor: Color = Color(0xFFEF4444)
) {
    val badgeColor = if (diff >= 0) positiveColor else negativeColor
    val prefix = if (diff > 0) "+" else ""
    val displayedDiff = diff.toTwoDecimalString()
    val arrowRotation = if (diff >= 0) 0f else 180f

    val digits = remember(value) {
        value.toTwoDecimalString().toList()
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.95f))
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = currencySymbol,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            digits.forEach { digit ->
                AnimatedDigit(
                    digit = digit,
                    fontSize = 32,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Badge variation
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .background(badgeColor)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowUpward,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(14.dp)
                    .graphicsLayer { rotationZ = arrowRotation }
            )

            Spacer(Modifier.width(4.dp))

            Text(
                text = "$prefix$displayedDiff%",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedDigit(
    digit: Char,
    fontSize: Int,
    fontWeight: FontWeight
) {
    AnimatedContent(
        targetState = digit,
        transitionSpec = {
            slideInVertically { it } + fadeIn() with
                    slideOutVertically { -it } + fadeOut()
        },
        label = "DigitCarousel"
    ) { targetDigit ->
        Text(
            text = targetDigit.toString(),
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            color = Color.Black
        )
    }
}

fun Double.toTwoDecimalString(): String {
    val rounded = (this * 100).toInt() / 100.0
    return rounded.toString()
}