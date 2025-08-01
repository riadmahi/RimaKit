package com.riadmahi.rimakit.components.swipecard

import androidx.compose.animation.core.Animatable
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
import androidx.compose.ui.draw.clip
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
fun SwipeCard(
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
    val thresholdX = 120f
    val thresholdY = 120f

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    val animatedOffsetX = remember { Animatable(0f) }
    val animatedOffsetY = remember { Animatable(0f) }
    var isAnimating by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    var nextAlpha by remember { mutableStateOf(0.8f) }
    var nextScale by remember { mutableStateOf(0.95f) }

    suspend fun resetPosition() {
        animatedOffsetX.animateTo(0f, animationSpec = tween(300))
        animatedOffsetY.animateTo(0f, animationSpec = tween(300))
        offsetX = 0f
        offsetY = 0f
    }

    suspend fun swipeAway(directionX: Float) {
        animatedOffsetX.animateTo(directionX * 600f, animationSpec = tween(300))
        animatedOffsetY.animateTo(0f, animationSpec = tween(300))
        delay(50)
        index = if (loop) (index + 1) % images.size else minOf(index + 1, images.size - 1)
        animatedOffsetX.snapTo(0f)
        animatedOffsetY.snapTo(0f)
        offsetX = 0f
        offsetY = 0f
        isAnimating = false
        current?.let { onSwipe(if (directionX > 0) "right" else "left", it) }
    }

    LaunchedEffect(offsetX, offsetY) {
        if (!isAnimating) {
            animatedOffsetX.snapTo(offsetX)
            animatedOffsetY.snapTo(offsetY)
        }
    }

    LaunchedEffect(index) {
        nextAlpha = 0.8f
        nextScale = 0.95f
        delay(100)
        nextAlpha = 1f
        nextScale = 1f

        delay(autoplayDelayMillis)
        if (!isAnimating) {
            isAnimating = true
            coroutineScope.launch { swipeAway(if ((index % 2 == 0)) 1f else -1f) }
        }
    }

    Box(
        modifier = modifier.size(cardSize.x, cardSize.y),
        contentAlignment = Alignment.Center
    ) {
        next?.let {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = nextScale
                        scaleY = nextScale
                        alpha = nextAlpha
                        shadowElevation = 4f
                        shape = RoundedCornerShape(24.dp)
                        clip = true
                    }
                    .zIndex(0f)
                    .size(cardSize.x, cardSize.y),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = it),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        current?.let {
            val rotationY = (animatedOffsetX.value / 300f).coerceIn(-1f, 1f) * 15f
            val rotationX = -(animatedOffsetY.value / 300f).coerceIn(-1f, 1f) * 15f
            val elevation = 16f + abs(animatedOffsetX.value + animatedOffsetY.value) / 10f
            val alpha = 1f - (abs(animatedOffsetX.value) / 600f).coerceIn(0f, 1f)
            val scale = 1f - (abs(animatedOffsetX.value) / 3000f).coerceIn(0f, 0.1f)

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        translationX = animatedOffsetX.value
                        translationY = animatedOffsetY.value
                        this.rotationY = rotationY
                        this.rotationX = rotationX
                        this.alpha = alpha
                        scaleX = scale
                        scaleY = scale
                        cameraDistance = 16 * density
                        shadowElevation = elevation
                        shape = RoundedCornerShape(24.dp)
                        clip = true
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                if (!isAnimating) {
                                    if (abs(offsetX) > thresholdX || abs(offsetY) > thresholdY) {
                                        isAnimating = true
                                        coroutineScope.launch {
                                            swipeAway(offsetX.sign.takeIf { it != 0f } ?: 1f)
                                        }
                                    } else {
                                        coroutineScope.launch { resetPosition() }
                                    }
                                }
                            },
                            onDrag = { _, dragAmount ->
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                            }
                        )
                    }
                    .zIndex(1f)
                    .size(cardSize.x, cardSize.y),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = it),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}