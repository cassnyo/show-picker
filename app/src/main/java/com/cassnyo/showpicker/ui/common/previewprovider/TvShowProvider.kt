package com.cassnyo.showpicker.ui.common.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.cassnyo.showpicker.data.mapper.mapBackdropPathToUrl
import com.cassnyo.showpicker.data.mapper.mapPosterPathToUrl
import com.cassnyo.showpicker.ui.model.TvShow
import java.time.LocalDate

class TvShowProvider: PreviewParameterProvider<TvShow> {

    override val values: Sequence<TvShow>
        get() = sequenceOf(
            TvShow(
                id = 130392,
                name = "The D'Amelio Show",
                overview = "The family you know and love is here with a brand new series, giving an all-access pass into their lives. Kris, Kourtney, Kim, Khloé, Kendall, and Kylie bring the cameras back to reveal the truth behind the headlines. From the intense pressures of running billion-dollar businesses to the hilarious joys of playtime and school drop-offs, this series brings viewers into the fold with a rivetingly honest story of love & life in the spotlight.",
                posterUrl = mapPosterPathToUrl("/3cJgB4fOfgHKNoHgtT3h1Qqkvxq.jpg"),
                backdropUrl = mapBackdropPathToUrl("/k3fV1avE7xcqIBD4sdhGlmU2VD5.jpg"),
                firstAirDate = LocalDate.parse("2022-04-14"),
                popularity = 169.406f,
                voteCount = 341,
                voteAverage = 9.4f,
                originCountry = listOf("US"),
                originalLanguage = "en"
            )
        )
}