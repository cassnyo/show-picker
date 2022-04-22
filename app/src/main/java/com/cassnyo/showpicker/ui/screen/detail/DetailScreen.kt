package com.cassnyo.showpicker.ui.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.cassnyo.showpicker.ui.common.RatingBar
import com.cassnyo.showpicker.ui.common.previewprovider.TvShowProvider
import com.cassnyo.showpicker.ui.model.TvShow
import com.skydoves.landscapist.glide.GlideImage

@Preview
@Composable
fun DetailScreen(
    @PreviewParameter(provider = TvShowProvider::class) tvShow: TvShow
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Backdrop(backdropUrl = tvShow.backdropUrl)

        Column(
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = tvShow.name.uppercase(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            RatingBar(
                rating = tvShow.voteAverage / 2f,
                starSize = 24.dp,
                displayValue = false,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MinDetail(
                    title = "Year",
                    value = tvShow.firstAirDate?.year.toString()
                )
                MinDetail(
                    title = "Country",
                    value = tvShow.originCountry.firstOrNull() ?: ""
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = tvShow.overview,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun Backdrop(
    backdropUrl: String?
) {
    GlideImage(
        imageModel = backdropUrl,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .shadow(2.dp)
    )
}

@Composable
fun MinDetail(
    title: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.caption
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

