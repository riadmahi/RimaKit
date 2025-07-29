package com.riadmahi.rimakit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.riadmahi.rimakit.components.minimalcard.MinimalCard
import com.riadmahi.rimakit.components.minimalcard.MinimalCardData
import org.jetbrains.compose.ui.tooling.preview.Preview

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
        Text("RimaKit Demo", style = MaterialTheme.typography.headlineMedium)
        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)

        Button(onClick = { onSelect("minimal_card") }) {
            Text("Minimal Card Demo")
        }

        // Future buttons for other components
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