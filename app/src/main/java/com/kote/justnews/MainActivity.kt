package com.kote.justnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.kote.justnews.ui.theme.JustNewsTheme
import dagger.hilt.android.AndroidEntryPoint
import com.kote.gnewsdecoder.GoogleNewsDecoder
import com.kote.justnews.ui.navigation.NavGraph
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        lifecycleScope.launch {
//            val decoder = GoogleNewsDecoder()
//            val result = decoder.decodeGoogleNewsUrl("https://news.google.com/rss/articles/CBMizAFBVV95cUxQVUNCdnRtQ2RPV3duRnJZVVQtMWI1SHZiME9sYjhFdTFOaXcyQ1lyaU5VRFZ2aWQtbGpWVU96dnFlcXpwaEtSOENJZndlQTlmX1NLbVZYQ1JfLXh0cUtzalJETkZFYk5kSVlJbExZY08wNXZVRkp5bHc2ZmpfbnYxU3lQV1RpdmcwcHhpQ1hQTzFOUkZEX0Vlbi1mc2R0eTdZWVBraUV3Yl9pUjdhaXdLcGJxOU12d0NYX0FoS2NtMWVDWXQ5eFF6RjFLdnXSAdIBQVVfeXFMTkw1UzAwZ0kyYk1Lcm9ObGRoRlhvQ1B4YWp2SEYtc0ViZlYzb0pPc0R1SXFvZGlhaEdEMlg0THBpa0RMc0pEZEd1N29EYmF0TUJCZFNLdzN5WXY2RW5xck85VEE2U2VyMDlqOHpHUmV6XzgwR3JWYkJULVN1cTNwTHlhSHFSZGNFdmItTWNtQmNTUHZCMmtCZzg2RlJfVVVxWFRPOVFaY29rQmM5Q0VkaWQ2Z1pXb1J0TlRHVlI4UmpKMVJfSkg1RjBieGNuOE9rMkd3?oc=5")
//            if (result["status"] == true) {
//                val decodedUrl = result["decodedUrl"] as String
//                println("decodedUrl: $decodedUrl")
//            } else {
//                val errorMessage = result["message"] as String
//                println(errorMessage)
//            }
//        }

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