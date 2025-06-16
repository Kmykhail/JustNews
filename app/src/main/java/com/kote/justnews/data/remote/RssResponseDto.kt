package com.kote.justnews.data.remote

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "rss", strict = false)
data class RssResponse(
    @field:Element(name = "channel")
    @param:Element(name = "channel")
    val channel: RssChannel = RssChannel()
)

@Root(name = "channel", strict = false)
data class RssChannel(
    @field:ElementList(inline = true, entry = "item", required = false)
    @param:ElementList(inline = true, entry = "item", required = false)
    val items: List<RssItem> = emptyList()
)

@Root(name = "source", strict = false)
data class RssSource(
    @field:Attribute(name = "url", required = false)
    @param:Attribute(name = "url", required = false)
    val url: String? = null,

    @field:Text(required = false)
    @param:Text(required = false)
    val name: String? = null
)

@Root(name = "item", strict = false)
data class RssItem(
    @field:Element(name = "title", required = false)
    @param:Element(name = "title", required = false)
    val title: String = "",

    @field:Element(name = "link", required = false)
    @param:Element(name = "link", required = false)
    val link: String = "",

    @field:Element(name = "pubDate", required = false)
    @param:Element(name = "pubDate", required = false)
    val pubDate: String = "",

    @field:Element(name = "description", required = false)
    @param:Element(name = "description", required = false)
    val description: String? = null,

    @field:Element(name = "source", required = false)
    @param:Element(name = "source", required = false)
    val source: RssSource? = null
)