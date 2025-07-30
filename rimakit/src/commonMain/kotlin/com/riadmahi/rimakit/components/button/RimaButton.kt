package com.riadmahi.rimakit.components.button

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.riadmahi.rimakit.theme.brSonomaTypography

@Composable
fun RimaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Black,
    contentColor: Color = Color.White,
    cornerRadius: Dp = 14.dp,
    textStyle: TextStyle = brSonomaTypography().titleSmall,
    isLoading: Boolean = false
) {
    var pressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    val alpha by animateFloatAsState(
        targetValue = if (pressed) 0.85f else 1f,
        animationSpec = tween(durationMillis = 250)
    )

    val animatedCornerRadius by animateDpAsState(
        targetValue = if (isLoading) 100.dp else cornerRadius,
        animationSpec = tween(durationMillis = 500)
    )

    val animatedPaddingHorizontal by animateDpAsState(
        targetValue = if (isLoading) 18.dp else 24.dp,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .alpha(alpha)
            .clip(RoundedCornerShape(animatedCornerRadius))
            .background(backgroundColor)
            .pointerInput(isLoading) {
                detectTapGestures(
                    onPress = {
                        if (!isLoading) {
                            pressed = true
                            val released = tryAwaitRelease()
                            pressed = false
                            if (released) onClick()
                        }
                    }
                )
            }
            .padding(horizontal = animatedPaddingHorizontal, vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = contentColor,
                strokeWidth = 2.dp,
                modifier = Modifier.size(18.dp)
            )
        } else {
            Text(
                text = text,
                style = textStyle,
                color = contentColor
            )
        }
    }
}