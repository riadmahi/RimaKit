package com.riadmahi.rimakit.components.tinderswipecard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.sign

@Composable
fun TinderSwipeCard(
    images: List<String>,
    modifier: Modifier = Modifier,
    cardSize: DpOffset = DpOffset(300.dp, 450.dp),
    autoplayDelayMillis: Long = 2000L,
    loop: Boolean = true,
    onSwipe: (direction: String, swipedUrl: String) -> Unit = { _, _ -> }
) {
    var index by remember { mutableStateOf(0) }
    val current = images.getOrNull(index % images.size)
    val next = images.getOrNull((index + 1) % images.size)

    val density = LocalDensity.current.density
    val coroutineScope = rememberCoroutineScope()

    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val scale = remember { Animatable(1f) }
    val alpha = remember { Animatable(1f) }
    val rotationZ = remember { Animatable(0f) }
    val nextScale = remember { Animatable(0.92f) }
    val nextAlpha = remember { Animatable(0.7f) }

    val threshold = 120f

    fun resetCard() {
        coroutineScope.launch {
            offsetX.animateTo(0f, tween(400, easing = EaseOutBack))
            offsetY.animateTo(0f, tween(400, easing = EaseOutBack))
            rotationZ.animateTo(0f, tween(400))
            scale.animateTo(1f, tween(400))
        }
    }

    fun swipeCard(direction: Float) {
        coroutineScope.launch {
            offsetX.animateTo(direction * 1000f, tween(350))
            alpha.animateTo(0f, tween(350))
            scale.animateTo(0.8f, tween(350))
            rotationZ.animateTo(direction * 18f, tween(350))

            delay(50)
            index = if (loop) (index + 1) % images.size else minOf(index + 1, images.size - 1)

            offsetX.snapTo(0f)
            offsetY.snapTo(0f)
            alpha.snapTo(1f)
            scale.snapTo(1f)
            rotationZ.snapTo(0f)
            nextScale.snapTo(0.92f)
            nextAlpha.snapTo(0.7f)

            current?.let { onSwipe(if (direction > 0) "right" else "left", it) }

            nextScale.animateTo(1f, tween(400, easing = EaseOutBack))
            nextAlpha.animateTo(1f, tween(400))
        }
    }

    LaunchedEffect(index) {
        delay(autoplayDelayMillis)
        swipeCard(1f)
    }

    Box(modifier = modifier.size(cardSize.x, cardSize.y), contentAlignment = Alignment.Center) {
        next?.let {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = nextScale.value
                        scaleY = nextScale.value
                        this.alpha = nextAlpha.value
                        shadowElevation = 4f
                        shape = RoundedCornerShape(24.dp)
                        clip = true
                    }
                    .zIndex(0f)
                    .size(cardSize.x, cardSize.y),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        current?.let {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        translationX = offsetX.value
                        translationY = offsetY.value
                        this.rotationZ = rotationZ.value
                        scaleX = scale.value
                        scaleY = scale.value
                        this.alpha = alpha.value
                        cameraDistance = 12 * density
                        shadowElevation = 16f
                        shape = RoundedCornerShape(24.dp)
                        clip = true
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                if (abs(offsetX.value) > threshold) {
                                    swipeCard(offsetX.value.sign)
                                } else {
                                    resetCard()
                                }
                            },
                            onDragCancel = {
                                resetCard()
                            },
                            onDrag = { _, dragAmount ->
                                coroutineScope.launch {
                                    offsetX.snapTo(offsetX.value + dragAmount.x)
                                    offsetY.snapTo(offsetY.value + dragAmount.y)
                                    rotationZ.snapTo((offsetX.value / 20f).coerceIn(-18f, 18f))
                                    scale.snapTo(1f - (abs(offsetX.value) / 2000f).coerceAtMost(0.1f))
                                }
                            }
                        )
                    }
                    .zIndex(1f)
                    .size(cardSize.x, cardSize.y),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}