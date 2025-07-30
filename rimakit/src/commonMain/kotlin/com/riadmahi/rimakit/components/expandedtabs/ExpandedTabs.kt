package com.riadmahi.rimakit.components.expandedtabs

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.riadmahi.rimakit.theme.brSonomaTypography
import org.jetbrains.compose.resources.painterResource

@Composable
fun ExpandedTabs(
    tabs: List<TabItem>,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    selectedBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    cornerRadius: Dp = 12.dp,
    typography: Typography = brSonomaTypography(),
    selectedTabIndex: Int? = null,
    onChange: (Int?) -> Unit = {}
) {
    var selectedIndex by remember(selectedTabIndex) { mutableStateOf(selectedTabIndex) }

    val outerShape = RoundedCornerShape(cornerRadius)
    val innerShape = RoundedCornerShape(cornerRadius / 1.5f)

    Row(
        modifier
            .clip(outerShape)
            .background(backgroundColor)
            .border(1.dp, borderColor, outerShape)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tabs.forEachIndexed { index, tab ->
            when (tab) {
                is TabItem.Separator -> {
                    Box(
                        modifier = Modifier
                            .width(1.2.dp)
                            .height(24.dp)
                            .background(borderColor)
                    )
                }

                is TabItem.Tab -> {
                    val isSelected = selectedIndex == index
                    val transition = updateTransition(isSelected, label = "TabTransition")

                    val padding by transition.animateDp(label = "padding") {
                        if (it) 16.dp else 8.dp
                    }

                    val gap by transition.animateDp(label = "gap") {
                        if (it) 8.dp else 0.dp
                    }

                    val textAlpha by transition.animateFloat(label = "alpha") {
                        if (it) 1f else 0f
                    }

                    Row(
                        modifier = Modifier
                            .clip(innerShape)
                            .background(if (isSelected) selectedBackgroundColor else Color.Transparent)
                            .clickable {
                                selectedIndex = index
                                onChange(index)
                            }
                            .padding(horizontal = padding, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(gap)
                    ) {
                        TabIconView(
                            icon = tab.icon,
                            contentDescription = tab.title,
                            tint = if (isSelected) activeColor else Color.Gray
                        )

                        if (isSelected) {
                            Text(
                                text = tab.title,
                                color = activeColor,
                                modifier = Modifier.alpha(textAlpha),
                                style = typography.labelLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabIconView(icon: TabIcon, contentDescription: String?, tint: Color) {
    when (icon) {
        is TabIcon.Vector -> Icon(
            imageVector = icon.icon,
            contentDescription = contentDescription,
            tint = tint
        )

        is TabIcon.Drawable -> {
            val painter: Painter = painterResource(icon.resId)
            Icon(
                painter = painter,
                contentDescription = contentDescription,
                tint = tint
            )
        }
    }
}