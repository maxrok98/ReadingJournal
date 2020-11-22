package com.example.readingjournal.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class BookWrapper(
    val items: List<BookProperty>
)

data class BookProperty(
    @Json(name = "volumeInfo")
    val info: BookInfo
)

data class BookInfo(
    val title: String,
    val authors: List<String>,
    @Json(name = "imageLinks")
    val imgSrcUrl: ImageList
)

data class ImageList(
    val smallThumbnail: String,
    val thumbnail: String
)

