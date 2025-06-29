package com.kote.justnews.data.remote

//{
//    "totalArticles": 54904,
//    "articles": [
//    {
//        "title": "Google's Pixel 7 and 7 Pro’s design gets revealed even more with fresh crisp renders",
//        "description": "Now we have a complete image of what the next Google flagship phones will look like. All that's left now is to welcome them during their October announcement!",
//        "content": "Google’s highly anticipated upcoming Pixel 7 series is just around the corner, scheduled to be announced on October 6, 2022, at 10 am EDT during the Made by Google event. Well, not that there is any lack of images showing the two new Google phones, b... [1419 chars]",
//        "url": "https://www.phonearena.com/news/google-pixel-7-and-pro-design-revealed-even-more-fresh-renders_id142800",
//        "image": "https://m-cdn.phonearena.com/images/article/142800-wide-two_1200/Googles-Pixel-7-and-7-Pros-design-gets-revealed-even-more-with-fresh-crisp-renders.jpg",
//        "publishedAt": "2022-09-28T08:14:24Z",
//        "source": {
//        "name": "PhoneArena",
//        "url": "https://www.phonearena.com"
//    }
//    }
//    ]
//}

data class GNewsResponse(
    val articles: List<GNewsArticle>
)

data class GNewsArticle(
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val image: String,
    val publishedAt: String,
    val source: Source,
)

data class Source(
    val url: String,
    val name: String
)