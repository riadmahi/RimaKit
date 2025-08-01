package com.riadmahi.rimakit.components.swipecard

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.rememberAsyncImagePainter
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sign

@Composable
fun SwipeCard(
    images: List<String>,
    modifier: Modifier = Modifier,
    maxVisibleCards: Int = 3,
    cardSize: DpOffset = DpOffset(300.dp, 450.dp),
    loop: Boolean = false,
    showSwipeButtons: Boolean = true,
    onSwipe: (direction: String, swipedUrl: String) -> Unit = { _, _ -> }
) {
    var cards by remember { mutableStateOf(images) }
    var dragOffsetX by remember { mutableStateOf(0f) }
    var isSwipingOut by remember { mutableStateOf(0) } // -1 left, 1 right, 0 none

    val swipeThreshold = cardSize.x.value * 0.3f

    // Animate top card offsetX when swiping out
    val animatedOffsetX by animateFloatAsState(
        targetValue = when (isSwipingOut) {
            1 -> 600f
            -1 -> -600f
            else -> dragOffsetX
        }
    )

    // Animate top card alpha and scale when swiping out
    val topCardAlpha by animateFloatAsState(
        targetValue = if (isSwipingOut != 0) 1f - (abs(animatedOffsetX) / 600f).coerceIn(0f, 1f) else 1f
    )
    val topCardScale by animateFloatAsState(
        targetValue = if (isSwipingOut != 0) 1f - (abs(animatedOffsetX) / 3000f).coerceIn(0f, 0.1f) else 1f
    )

    // When swipe out animation is finished, remove the top card and reset states
    LaunchedEffect(isSwipingOut, animatedOffsetX) {
        if (isSwipingOut != 0 && abs(animatedOffsetX) >= 600f) {
            if (cards.isNotEmpty()) {
                val swiped = cards.first()
                onSwipe(if (isSwipingOut == 1) "right" else "left", swiped)
                cards = cards.drop(1)
                if (loop) cards = cards + swiped
            }
            dragOffsetX = 0f
            isSwipingOut = 0
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val visibleCards = cards.take(maxVisibleCards)

        val underCards = visibleCards.dropLast(1)
        val topCardUrl = visibleCards.lastOrNull()

        Box(
            modifier = Modifier.size(cardSize.x, cardSize.y),
            contentAlignment = Alignment.Center
        ) {
            // Compose underCards from bottom (index 0) to top (index N-2)
            underCards.forEachIndexed { index, url ->
                // Under cards react to dragOffsetX for scale, alpha, yOffset
                val fraction = 1f - (index * 0.07f)
                val baseScale = 0.85f + (index * 0.07f)
                val baseAlpha = 0.7f - (index * 0.15f)
                val baseYOffset = (index * 20).dp

                // Calculate animated values based on dragOffsetX
                val dragFraction = (dragOffsetX / cardSize.x.value).coerceIn(-1f, 1f)
                val scaleTarget = baseScale + (0.05f * (1 - index) * abs(dragFraction))
                val alphaTarget = baseAlpha + (0.1f * (1 - index) * abs(dragFraction))
                val yOffsetTarget = baseYOffset - animateDpAsState(targetValue = (10.dp * abs(dragFraction) * (1 - index))).value

                val scale by animateFloatAsState(targetValue = scaleTarget.coerceIn(0.85f, 1f))
                val alpha by animateFloatAsState(targetValue = alphaTarget.coerceIn(0.5f, 1f))

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            shadowElevation = ((maxVisibleCards - index) * 6).dp.value
                            shape = RoundedCornerShape(28.dp)
                            clip = true
                        }
                        .offset { IntOffset(0, yOffsetTarget.roundToPx()) }
                        .size(cardSize.x, cardSize.y)
                        .background(Color.Gray)
                        .zIndex(index.toFloat()),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(url),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            if (topCardUrl != null) {
                // Top card - swipable
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            translationX = animatedOffsetX
                            scaleX = topCardScale
                            scaleY = topCardScale
                            alpha = topCardAlpha
                            rotationZ = (animatedOffsetX / cardSize.x.value) * 12f
                            shadowElevation = 24f
                            shape = RoundedCornerShape(28.dp)
                            clip = true
                        }
                        .size(cardSize.x, cardSize.y)
                        .background(Color.Gray)
                        .pointerInput(cards) {
                            detectDragGestures(
                                onDragEnd = {
                                    if (abs(dragOffsetX) > swipeThreshold) {
                                        isSwipingOut = dragOffsetX.sign.toInt()
                                    } else {
                                        dragOffsetX = 0f
                                    }
                                },
                                onDragCancel = {
                                    dragOffsetX = 0f
                                },
                                onDrag = { _, dragAmount ->
                                    dragOffsetX += dragAmount.x
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(topCardUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        if (showSwipeButtons && cards.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (cards.isNotEmpty() && isSwipingOut == 0) {
                            isSwipingOut = -1
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    modifier = Modifier.width(120.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Swipe Left")
                }
                Button(
                    onClick = {
                        if (cards.isNotEmpty() && isSwipingOut == 0) {
                            isSwipingOut = 1
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.width(120.dp)
                ) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Swipe Right")
                }
            }
        }
    }
}