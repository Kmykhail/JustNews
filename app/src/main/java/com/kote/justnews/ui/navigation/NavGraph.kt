package com.kote.justnews.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kote.justnews.ui.screens.NewsViewModel
import com.kote.justnews.ui.screens.ArticleScreen
import com.kote.justnews.ui.screens.HomeScreen
import java.net.URLDecoder

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            HomeScreen(
                homeViewModel = hiltViewModel<NewsViewModel>(),
                onNavigateToSelectedArticle = { url ->
                    val encodedUrl = Uri.encode(url)
                    navController.navigate("article/$encodedUrl")
                },
                modifier = modifier
            )
        }
        composable(
            route = "article/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val rawUrl = backStackEntry.arguments?.getString("url") ?: ""
            val decodedUrl = URLDecoder.decode(rawUrl, "UTF-8")
            ArticleScreen(url = decodedUrl)
        }
    }
}
