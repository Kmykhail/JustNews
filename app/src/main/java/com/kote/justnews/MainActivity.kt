package com.kote.justnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kote.justnews.ui.screens.NewsViewModel
import com.kote.justnews.ui.theme.JustNewsTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.kote.justnews.ui.navigation.NavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JustNewsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(
                        navController = rememberNavController(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NewsScreen(
    viewModel: NewsViewModel = hiltViewModel(),
    modifier: Modifier
) {
    val newsState by viewModel.state.collectAsStateWithLifecycle()
    viewModel.loadNews("business")

//    ArticleScreen("https://apnews.com/article/us-mint-treasury-department-penny-end-production-daf6367d7e8d31d6783720d5d4667115")
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = modifier
//    ) {
//        when (newsState) {
//            is NewsState.Success -> {
//                NewsList((newsState as NewsState.Success).news)
//            }
//            is NewsState.Loading -> {
//                LoadingScreen()
//            }
//            is NewsState.Error -> {
//                ErrorScreen((newsState as NewsState.Error).message)
//            }
//        }
//    }
}

//@Composable
//fun NewsList(
//    news: List<News>
//) {
//    var selectedUrl = remember { "" }
//    ArticleScreen(selectedUrl)
//
//    LazyColumn(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        items(items = news) { item ->
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clickable{
//                        selectedUrl = item.url
//                        Log.d("WTF", "clicked url ${item.url}")
//                    }
//            ) {
//                Column {
//                    Text(
//                        text = item.title,
//                        style = MaterialTheme.typography.titleMedium
//                    )
//                    Text(
//                        text = item.source.name ?: "Source name is null"
//                    )
//                    Text(text = item.url)
//                    Log.d("WTF", item.url)
//                }
//            }
//            Spacer(modifier = Modifier.height(4.dp))
//        }
//    }
//}
//
