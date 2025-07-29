package com.riadmahi.rimakit.components.expandedtabs

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp

@Composable
fun ExpandedTabs(
    tabs: List<TabItem>,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    onChange: (Int?) -> Unit = {}
) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    Row(
        modifier
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.background)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(24.dp))
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
                            .background(MaterialTheme.colorScheme.outline)
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
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent)
                            .clickable {
                                selectedIndex = index
                                onChange(index)
                            }
                            .padding(horizontal = padding, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(gap)
                    ) {
                        Icon(tab.icon, contentDescription = tab.title, tint = if (isSelected) activeColor else Color.Gray)
                        if (isSelected) {
                            Text(
                                text = tab.title,
                                color = activeColor,
                                modifier = Modifier.alpha(textAlpha),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }
    }
}