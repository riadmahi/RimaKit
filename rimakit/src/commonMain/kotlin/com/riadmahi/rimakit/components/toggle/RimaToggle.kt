package com.riadmahi.rimakit.components.toggle

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RimaToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    width: Dp = 52.dp,
    height: Dp = 32.dp,
    thumbSize: Dp = 24.dp,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = Color.LightGray,
    thumbColor: Color = Color.White,
    disabled: Boolean = false
) {
    val toggleColor = if (disabled) inactiveColor.copy(alpha = 0.5f) else if (checked) activeColor else inactiveColor
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) width - thumbSize - 4.dp else 4.dp,
        animationSpec = tween(durationMillis = 250), label = "ThumbOffset"
    )
    val thumbAlpha by animateFloatAsState(
        targetValue = if (disabled) 0.5f else 1f,
        animationSpec = tween(durationMillis = 250), label = "ThumbAlpha"
    )

    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(100))
            .background(toggleColor)
            .clickable(enabled = !disabled) { onCheckedChange(!checked) },
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .size(thumbSize)
                .clip(CircleShape)
                .background(thumbColor)
                .alpha(thumbAlpha)
        )
    }
}