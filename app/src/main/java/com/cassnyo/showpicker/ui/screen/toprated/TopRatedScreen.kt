package com.cassnyo.showpicker.ui.screen.toprated

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cassnyo.showpicker.R
import com.cassnyo.showpicker.ui.common.RatingBar
import com.cassnyo.showpicker.ui.model.TvShow
import com.cassnyo.showpicker.ui.theme.ColorTvShowCardContainer
import com.cassnyo.showpicker.ui.theme.ColorTvShowCardOnContainer
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalFoundationApi
@Composable
fun TopRatedScreen(
    viewModel: TopRatedViewModel = viewModel()
) {
    val tvShows by viewModel.tvShows.collectAsState(emptyList())
    val viewMode by viewModel.viewMode.collectAsState(ViewMode.List)
    val isLoading by viewModel.isLoading.collectAsState(false)

    Column {
        Header(
            viewMode = viewMode,
            onModeSelected = { selectedMode ->
                viewModel.toggleViewMode(selectedMode)
            }
        )

        Box {
            if (isLoading) {
                Loading()
            }

            TopRatedTvShows(
                tvShowList = tvShows,
                onTvShowClick = {},
                onLoadMore = { viewModel.loadTopRatedTvShows() },
                viewMode = viewMode
            )
        }
    }

}

@Composable
fun Header(
    viewMode: ViewMode,
    onModeSelected: (ViewMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 32.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold
            )

            ViewModeSelector(viewMode, onModeSelected)
        }
    }
}

@Composable
fun Loading() {
    CircularProgressIndicator(
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun ViewModeSelector(
    viewMode: ViewMode,
    onModeSelected: (ViewMode) -> Unit
) {
    when (viewMode) {
        ViewMode.Grid -> IconButton(onClick = { onModeSelected(ViewMode.List) }) {
            Icon(Icons.Filled.ViewList, "Toggle list view")
        }
        ViewMode.List -> IconButton(onClick = { onModeSelected(ViewMode.Grid) }) {
            Icon(Icons.Filled.GridView, "Toggle grid view")
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun TopRatedTvShows(
    tvShowList: List<TvShow>,
    onTvShowClick: (TvShow) -> Unit,
    onLoadMore: () -> Unit,
    viewMode: ViewMode,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val contentPadding = PaddingValues(start = 32.dp, top = 16.dp, end = 32.dp, bottom = 32.dp)
    val verticalArrangement = Arrangement.spacedBy(16.dp)

    when (viewMode) {
        ViewMode.Grid -> TopRatedGrid(listState, contentPadding, verticalArrangement, tvShowList, onTvShowClick, onLoadMore)
        ViewMode.List -> TopRatedList(listState, contentPadding, verticalArrangement, tvShowList, onTvShowClick, onLoadMore)
    }
}

@Composable
private fun TopRatedList(
    listState: LazyListState,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.HorizontalOrVertical,
    tvShowList: List<TvShow>,
    onTvShowClick: (TvShow) -> Unit,
    onLoadMore: () -> Unit
) {
    val lastIndex = tvShowList.lastIndex
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        itemsIndexed(tvShowList) { index, tvShow ->
            if (index == lastIndex) {
                LaunchedEffect(Unit) { onLoadMore() }
            }
            TvShowListItem(tvShow = tvShow, onClick = onTvShowClick)
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun TopRatedGrid(
    listState: LazyListState,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.HorizontalOrVertical,
    tvShowList: List<TvShow>,
    onTvShowClick: (TvShow) -> Unit,
    onLoadMore: () -> Unit
) {
    val lastIndex = tvShowList.lastIndex
    LazyVerticalGrid(
        state = listState,
        cells = GridCells.Fixed(2),
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        itemsIndexed(tvShowList) { index, tvShow ->
            if (index == lastIndex) {
                LaunchedEffect(Unit) { onLoadMore() }
            }
            TvShowGridItem(tvShow = tvShow, onClick = onTvShowClick)
        }
    }
}

@Composable
fun TvShowGridItem(
    tvShow: TvShow,
    onClick: (TvShow) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(tvShow) }
    ) {
        GlideImage(
            imageModel = tvShow.posterUrl,
            modifier = Modifier
                .size(
                    width = 140.dp,
                    height = 200.dp
                )
                .clip(RoundedCornerShape(size = 4.dp))
        )

        Spacer(modifier = Modifier.height(2.dp))

        Column(
            modifier = Modifier.width(140.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = tvShow.name,
                style = MaterialTheme.typography.subtitle2
            )

            RatingBar(tvShow.voteAverage / 2f)
        }
    }
}

@Composable
fun TvShowListItem(
    tvShow: TvShow,
    onClick: (TvShow) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(tvShow) },
        backgroundColor = ColorTvShowCardContainer
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            // First half - poster
            GlideImage(
                imageModel = tvShow.posterUrl,
                modifier = Modifier
                    .size(
                        width = 80.dp,
                        height = 120.dp
                    )
                    .clip(RoundedCornerShape(size = 4.dp))
            )

            // Second half - name + vote average
            Column(
                modifier = Modifier
                    .height(120.dp)
                    .padding(
                        horizontal = 16.dp
                    ),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = tvShow.name,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = ColorTvShowCardOnContainer
                    )

                    Text(
                        text = tvShow.overview,
                        style = MaterialTheme.typography.caption,
                        color = ColorTvShowCardOnContainer,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                RatingBar(rating = tvShow.voteAverage / 2f)
            }
        }
    }
}