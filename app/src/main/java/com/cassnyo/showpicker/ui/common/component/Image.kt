package com.cassnyo.showpicker.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.cassnyo.showpicker.ui.theme.ColorOnSecondary
import com.cassnyo.showpicker.ui.theme.ColorSecondary

@Composable
fun ImageLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ColorSecondary)
    ) {
        CircularProgressIndicator(
            color = ColorOnSecondary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ImageLoadError(
    iconSize: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ColorSecondary)
    ) {
        Icon(
            imageVector = Icons.Rounded.BrokenImage,
            contentDescription = "Image not loaded",
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.Center),
            tint = ColorOnSecondary
        )
    }
}