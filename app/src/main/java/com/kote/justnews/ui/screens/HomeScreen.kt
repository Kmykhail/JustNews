package com.kote.justnews.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kote.justnews.domain.model.News

@Composable
fun HomeScreen(
    homeViewModel: NewsViewModel,
    onNavigateToSelectedArticle: (String) -> Unit,
    modifier: Modifier
) {
    val newsState by homeViewModel.state.collectAsStateWithLifecycle()
//    homeViewModel.loadTopHeadlines()
    Scaffold(
        bottomBar = {BottomBar(onClick =  {})},
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (newsState) {
                is NewsState.Success -> {
                    NewsList(
                        newsList = (newsState as NewsState.Success).currentNews,
                        onClick = onNavigateToSelectedArticle,
                    )
                }
                is NewsState.Loading -> {
                    LoadingScreen()
                }
                is NewsState.Error -> {
                    ErrorScreen((newsState as NewsState.Error).message)
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun BottomBar(
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
    ) {
        Text(
            text = "More news",
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Composable
fun NewsList(
    newsList: List<News>,
    onClick: (String) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = newsList) { item->
            NewsItem(item, onClick)
        }
    }
}

@Composable
fun NewsItem(news: News, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 4.dp)
            .clickable(onClick = {onClick(news.link)})
    ) {
        Column {
            news.imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = news.description
            )
        }
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Error: $message")
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
