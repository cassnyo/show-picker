package com.cassnyo.showpicker.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cassnyo.showpicker.ui.theme.ColorStarEmpty
import com.cassnyo.showpicker.ui.theme.ColorStarFull

@Preview
@Composable
fun RatingBarPreview() {
    RatingBar(4f)
}

@Composable
fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier,
    starSize: Dp = 16.dp,
    displayValue: Boolean = true
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        (0 until 5).forEach { index ->
            if (index < rating) {
                StarFull(modifier = Modifier.size(starSize))
            } else {
                StarEmpty(modifier = Modifier.size(starSize))
            }
        }

        Spacer(modifier = Modifier.size(4.dp))

        if (displayValue) {
            Text(
                text = rating.toString(),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
private fun StarFull(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Rounded.Star,
        contentDescription = "Full star",
        modifier = modifier,
        tint = ColorStarFull
    )
}

@Composable
private fun StarEmpty(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Rounded.Star,
        contentDescription = "Empty start",
        modifier = modifier,
        tint = ColorStarEmpty,
    )
}