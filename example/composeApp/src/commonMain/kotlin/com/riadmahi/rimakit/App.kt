package com.riadmahi.rimakit

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import com.riadmahi.rimakit.components.expandedtabs.ExpandedTabs
import com.riadmahi.rimakit.components.expandedtabs.TabItem

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.riadmahi.rimakit.components.animatednumber.AnimatedNumberRandom
import com.riadmahi.rimakit.components.expandedtabs.TabIcon
import com.riadmahi.rimakit.components.minimalcard.MinimalCard
import com.riadmahi.rimakit.components.minimalcard.MinimalCardData
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import rimakit.example.composeapp.generated.resources.Res
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .safeContentPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_brand),
            contentDescription = null,
            modifier = Modifier.height(80.dp)
        )
        HorizontalDivider(color =  Color(0xFFE6E6E7), thickness = 1.dp)

        Button(onClick = { onSelect("minimal_card") }) {
            Text("Minimal Card Demo")
        }
        Button(onClick = { onSelect("expanded_tabs") }) {
            Text("Expanded Tabs Demo")
        }
        Button(onClick = { onSelect("animated_number") }) {
            Text("Animated Number Demo")
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
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onBack) {
            Text("← Back")
        }

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
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Button(onClick = onBack) {
            Text("← Back")
        }

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
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Button(onClick = onBack) {
            Text("← Back")
        }

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