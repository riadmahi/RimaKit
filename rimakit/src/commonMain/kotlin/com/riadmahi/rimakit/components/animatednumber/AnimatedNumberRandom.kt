package com.riadmahi.rimakit.components.animatednumber

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with

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
            fontWeight = fontWeight
        )
    }
}

@Composable
fun AnimatedNumberRandom(
    value: Double,
    diff: Double,
    modifier: Modifier = Modifier,
    currencySymbol: String = "$",
    positiveColor: Color = Color(0xFF34D399),
    negativeColor: Color = Color(0xFFEF4444)
) {
    val animatedValue by animateFloatAsState(
        targetValue = value.toFloat(),
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
    )
    val animatedDiff by animateFloatAsState(
        targetValue = diff.toFloat(),
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
    )
    val rotation by animateFloatAsState(
        targetValue = if (diff >= 0) 0f else 180f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
    )

    val badgeColor = if (diff >= 0) positiveColor else negativeColor
    val prefix = if (diff > 0) "+" else ""
    val formattedValue = ((animatedValue * 100).toInt() / 100.0).toString()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        // Carousel style currency + digits
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = currencySymbol,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )
            for (digit in formattedValue) {
                AnimatedDigit(
                    digit = digit,
                    fontSize = 32,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Diff badge
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = badgeColor, shape = RoundedCornerShape(100))
                .padding(horizontal = 10.dp, vertical = 4.dp)
                .animateContentSize()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowUpward,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(14.dp)
                    .graphicsLayer { rotationZ = rotation }
            )

            Spacer(Modifier.width(4.dp))

            Text(
                text = "$prefix${((animatedDiff * 100).toInt() / 100.0)}%",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}