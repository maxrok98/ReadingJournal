package com.example.readingjournal.network

import com.example.readingjournal.models.Book
import com.example.readingjournal.models.LastPublishedBook
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class BookWrapper(
    val items: List<BookProperty>
)
{
    fun asLastBookDataBaseModel(): LastPublishedBook {
        return  LastPublishedBook(Id = 1,
            Author = items[0].info.authors[0],
            Title = items[0].info.title,
            publishDate = items[0].info.publishedDate,
            thumbnailURL = items[0].info.imgSrcUrl.thumbnail)

    }
}




data class BookProperty(
    @Json(name = "volumeInfo")
    val info: BookInfo
)

data class BookInfo(
    val title: String,
    val authors: List<String>,
    @Json(name = "publishedDate")
    val publishedDate: String,
    @Json(name = "imageLinks")
    val imgSrcUrl: ImageList
)

data class ImageList(
    val smallThumbnail: String,
    val thumbnail: String
)

