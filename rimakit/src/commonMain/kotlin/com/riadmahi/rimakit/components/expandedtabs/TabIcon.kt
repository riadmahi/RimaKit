package com.riadmahi.rimakit.components.expandedtabs

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.DrawableResource

sealed interface TabIcon {
    data class Vector(val icon: ImageVector) : TabIcon
    data class Drawable(val resId: DrawableResource) : TabIcon
}