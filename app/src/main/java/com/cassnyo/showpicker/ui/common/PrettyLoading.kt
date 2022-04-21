package com.cassnyo.showpicker.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PrettyLoading(
    message: String?,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size)
                .padding(4.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colors.primaryVariant.copy(alpha = 0.8f),
                    shape = CircleShape
                )
                .padding(4.dp),
            strokeWidth = 3.dp
        )

        if (message != null) {
            Text(
                text = message,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium
            )
        }
    }
}