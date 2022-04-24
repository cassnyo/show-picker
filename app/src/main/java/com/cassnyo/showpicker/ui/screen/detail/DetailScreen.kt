package com.cassnyo.showpicker.ui.screen.detail

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cassnyo.showpicker.R
import com.cassnyo.showpicker.ui.common.component.RatingBar
import com.cassnyo.showpicker.ui.model.TvShow
import com.cassnyo.showpicker.ui.theme.ColorBackground
import com.cassnyo.showpicker.ui.theme.ColorTvShowCardContainer
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.skydoves.landscapist.glide.GlideImage
import java.util.Locale
import kotlin.math.absoluteValue

@ExperimentalPagerApi
@Composable
fun DetailScreen(
    navController: NavController,
) {
    val viewModel: DetailViewModel = hiltViewModel()
    val uiState = viewModel.uiState
    val tvShows = uiState.tvShows
    val context = LocalContext.current
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
    ) {
        Header(
            onBackClick = { navController.navigateUp() },
            onShareClick = {
                shareTvShow(tvShows[pagerState.currentPage], context)
            }
        )
        if (tvShows.isNotEmpty()) {
            TvShowPager(
                tvShows = tvShows,
                state = pagerState,
                onLoadMore = { viewModel.loadSimilarTvShows() }
            )
        }
    }
}

@Composable
private fun Header(
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(36.dp)
                )
            }
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Rounded.Share,
                    contentDescription = "Share"
                )
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun TvShowPager(
    tvShows: List<TvShow>,
    state: PagerState,
    onLoadMore: () -> Unit
) {
    HorizontalPager(
        count = tvShows.size,
        contentPadding = PaddingValues(32.dp),
        modifier = Modifier.fillMaxSize(),
        state = state,
    ) { page ->
        if (page == tvShows.size - 1) {
            LaunchedEffect(Unit) { onLoadMore() }
        }
        TvShowDetail(
            tvShow = tvShows[page],
            modifier = Modifier.graphicsLayer {
                animateTransition(this@HorizontalPager, page, this)
            }
        )
    }
}

@ExperimentalPagerApi
private fun animateTransition(
    pagerScope: PagerScope,
    page: Int,
    graphicsLayerScope: GraphicsLayerScope
) = with(graphicsLayerScope) {
    val pageOffset = pagerScope.calculateCurrentOffsetForPage(page).absoluteValue
    // We animate the scaleX + scaleY, between 85% and 100%
    lerp(
        start = 0.85f,
        stop = 1f,
        fraction = 1f - pageOffset.coerceIn(0f, 1f)
    ).also { scale ->
        scaleX = scale
        scaleY = scale
    }

    // We animate the alpha, between 50% and 100%
    alpha = lerp(
        start = 0.5f,
        stop = 1f,
        fraction = 1f - pageOffset.coerceIn(0f, 1f)
    )
}

@Composable
private fun TvShowDetail(
    tvShow: TvShow,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            // Card background doesn't work properly if the color has transparency
            modifier = Modifier.background(ColorTvShowCardContainer)
        ) {
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
                    ColumnDetail(
                        title = stringResource(R.string.detail_title_year),
                        value = tvShow.firstAirDate?.year.toString()
                    )
                    ColumnDetail(
                        title = stringResource(R.string.detail_title_country),
                        value = tvShow.originCountry.firstOrNull()
                            ?: stringResource(R.string.detail_country_placeholder)
                    )
                    ColumnDetail(
                        title = stringResource(R.string.detail_title_language),
                        value = Locale(tvShow.originalLanguage).displayLanguage
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
}

@Composable
private fun Backdrop(
    backdropUrl: String?,
) {
    GlideImage(
        imageModel = backdropUrl,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .shadow(2.dp),
        loading = {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    )
}

@Composable
private fun ColumnDetail(
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

private fun shareTvShow(tvShow: TvShow, context: Context) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, tvShow.contentUrl)
        type = "text/plain"
    }

    context.startActivity(intent)
}