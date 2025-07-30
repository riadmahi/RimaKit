package com.riadmahi.rimakit.components.expandedtabs

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.DrawableResource

sealed class TabItem {
    data class Tab(val title: String, val icon: TabIcon) : TabItem()
    data object Separator : TabItem()
}

fun TabItem(title: String, icon: ImageVector): TabItem.Tab =
    TabItem.Tab(title, TabIcon.Vector(icon))

fun TabItem(title: String, icon: DrawableResource): TabItem.Tab =
    TabItem.Tab(title, TabIcon.Drawable(icon))