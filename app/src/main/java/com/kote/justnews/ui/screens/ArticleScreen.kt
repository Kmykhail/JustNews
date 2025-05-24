package com.kote.justnews.ui.screens

import android.os.Build
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ArticleScreen(url: String) {
    WebViewExample(url)
}

@Composable
private fun WebViewExample(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true // Для HTML5

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)

                        // Видалити небажані елементи через JavaScript
                        view?.evaluateJavascript(
                            """
                            // Приклад для Wired.com (може змінюватись)
                            document.querySelector('header').remove();
                            document.querySelector('footer').remove();
                            document.querySelector('.StandardNavigationHeadLineBlock').remove();

                            // Зробити контент на весь екран
                            document.body.style.margin = '0';
                            document.body.style.padding = '0';
                            """.trimIndent(),
                            null
                        )
                    }
                }
                loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}