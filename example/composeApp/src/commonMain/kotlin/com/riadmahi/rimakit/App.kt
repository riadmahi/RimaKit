package com.riadmahi.rimakit

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.riadmahi.rimakit.components.animatednumber.AnimatedNumberRandom
import com.riadmahi.rimakit.components.expandedtabs.ExpandedTabs
import com.riadmahi.rimakit.components.expandedtabs.TabItem
import com.riadmahi.rimakit.components.minimalcard.MinimalCard
import com.riadmahi.rimakit.components.minimalcard.MinimalCardData
import com.riadmahi.rimakit.theme.brSonomaTypography
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import rimakit.example.composeapp.generated.resources.Res
import rimakit.example.composeapp.generated.resources.ic_arrow_left
import rimakit.example.composeapp.generated.resources.ic_brand
import rimakit.example.composeapp.generated.resources.ic_home
import rimakit.example.composeapp.generated.resources.ic_notification
import rimakit.example.composeapp.generated.resources.ic_search

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            NavHost(navController = navController, startDestination = "menu") {
                composable("menu") {
                    ComponentMenu(onSelect = { navController.navigate(it) })
                }
                composable("minimal_card") {
                    MinimalCardDemo(onBack = { navController.popBackStack() })
                }
                composable("expanded_tabs") {
                    ExpandedTabsDemo(onBack = { navController.popBackStack() })
                }
                composable("animated_number") {
                    AnimatedNumberRandomDemo(onBack = { navController.popBackStack() })
                }
            }
        }
    }
}
@Composable
fun ComponentMenu(onSelect: (String) -> Unit) {
    val typography = brSonomaTypography()

    val items = listOf(
        "Minimal Card Demo" to "minimal_card",
        "Expanded Tabs Demo" to "expanded_tabs",
        "Animated Number Demo" to "animated_number"
    )

    var selected by remember { mutableStateOf<String?>(null) }
    var pendingRoute by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(pendingRoute) {
        if (pendingRoute != null) {
            delay(250)
            onSelect(pendingRoute!!)
            pendingRoute = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 32.dp, end = 32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(Res.drawable.ic_brand),
                contentDescription = "Brand",
                modifier = Modifier.height(72.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { (label, route) ->
                val isSelected = selected == route
                val underlineWidth by animateDpAsState(if (isSelected) 24.dp else 0.dp)
                val textAlpha by animateFloatAsState(if (isSelected) 1f else 0.6f)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selected = route
                            pendingRoute = route
                        }
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = label,
                        style = typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.alpha(textAlpha)
                    )

                    Box(
                        Modifier
                            .height(2.dp)
                            .width(underlineWidth)
                            .background(Color.Black)
                    )
                }
            }
        }
    }
}
@Composable
fun MinimalCardDemo(onBack: () -> Unit) {
    val cards = listOf(
        MinimalCardData(
            title = "Sick title",
            description = "How to design with gestures and motion that feel intuitive and natural.",
            imageUrl = "https://plus.unsplash.com/premium_photo-1753227114328-996fb7f092b9?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxmZWF0dXJlZC1waG90b3MtZmVlZHw5fHx8ZW58MHx8fHx8"
        ),
        MinimalCardData(
            title = "Another card",
            description = "A different card with great UX advice.",
            imageUrl = "https://plus.unsplash.com/premium_photo-1753227114328-996fb7f092b9?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxmZWF0dXJlZC1waG90b3MtZmVlZHw5fHx8ZW58MHx8fHx8"
        ),
        MinimalCardData(
            title = "Final one",
            description = "Because 3 cards make a nice layout.",
            imageUrl = "https://plus.unsplash.com/premium_photo-1753227114328-996fb7f092b9?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxmZWF0dXJlZC1waG90b3MtZmVlZHw5fHx8ZW58MHx8fHx8"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RimaTopBar(title = "Minimal Card Demo", onBack = onBack)

        Spacer(modifier = Modifier.height(16.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            cards.forEach {
                MinimalCard(
                    title = it.title,
                    description = it.description,
                    imageUrl = it.imageUrl,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun ExpandedTabsDemo(onBack: () -> Unit) {
    val items = listOf(
        TabItem("Home", Res.drawable.ic_home),
        TabItem("Search", Res.drawable.ic_search),
        TabItem.Separator,
        TabItem("Notifications", Res.drawable.ic_notification)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        RimaTopBar(title = "Expanded Tabs Demo", onBack = onBack)

        ExpandedTabs(
            tabs = items,
            modifier = Modifier.padding(16.dp),
            onChange = { index ->
                println("Selected: $index")
            },
            activeColor = Color.Black,
            backgroundColor = Color.White,
            borderColor = Color(0xFFE6E6E7),
            selectedBackgroundColor = Color(0xFFF4F6F8),
            selectedTabIndex = 0
        )
    }
}

@Composable
fun AnimatedNumberRandomDemo(onBack: () -> Unit) {
    val value = remember { mutableStateOf(1234.56) }
    val diff = remember { mutableStateOf(4.5) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        RimaTopBar(title = "Animated Number Random Demo", onBack = onBack)
        Button(onClick = {
            value.value = (1000..5000).random().toDouble()
            diff.value = (-10..10).random().toDouble()
        }) {
            Text("Randomize")
        }

        AnimatedNumberRandom(
            value = value.value,
            diff = diff.value
        )
    }
}

@Composable
fun RimaTopBar(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = brSonomaTypography()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { onBack() }
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_arrow_left),
                contentDescription = "Back",
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            style = typography.titleMedium,
            color = Color.Black
        )
    }
}