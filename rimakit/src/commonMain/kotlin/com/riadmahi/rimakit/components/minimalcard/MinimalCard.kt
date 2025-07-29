package com.riadmahi.rimakit.components.minimalcard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.riadmahi.rimakit.theme.BorderColor
import com.riadmahi.rimakit.theme.CardColor
import com.riadmahi.rimakit.theme.brSonomaFontFamily
import com.riadmahi.rimakit.theme.brSonomaTypography

@Composable
fun MinimalCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    description: String,
    typography: Typography = brSonomaTypography()
) {
    Column(
        modifier = modifier
            .width(360.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardColor)
            .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .background(Color.White)
                .border(1.dp, BorderColor, RoundedCornerShape(15.dp))
                .padding(4.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )
        Column(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = title,
                style = typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description,
                style = typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}