package com.riadmahi.rimakit.components.expandedtabs

import androidx.compose.ui.graphics.vector.ImageVector

sealed class TabItem {
    data class Tab(val title: String, val icon: ImageVector) : TabItem()
    data object Separator : TabItem()
}