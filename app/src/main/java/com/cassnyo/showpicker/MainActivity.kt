package com.cassnyo.showpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.cassnyo.showpicker.ui.common.navigation.NavigationRoutes
import com.cassnyo.showpicker.ui.screen.detail.DetailScreen
import com.cassnyo.showpicker.ui.screen.toprated.TopRatedScreen
import com.cassnyo.showpicker.ui.theme.ShowPickerTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowPickerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = NavigationRoutes.TOP_RATED
                    ) {
                        composable(
                            route = NavigationRoutes.TOP_RATED,
                            // Slide from/to the left
                            enterTransition = { slideInHorizontally(initialOffsetX = { -it })},
                            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
                        ) { TopRatedScreen(navController) }

                        composable(
                            route = NavigationRoutes.DETAIL,
                            // Slide from/to the right
                            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
                            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
                        ) { DetailScreen(navController) }
                    }
                }
            }
        }
    }
}