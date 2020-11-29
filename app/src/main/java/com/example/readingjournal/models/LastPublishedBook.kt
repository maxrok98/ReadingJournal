package com.example.readingjournal.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_book_table")
data class LastPublishedBook(
    @PrimaryKey
    var Id: Long = 1,

    @ColumnInfo(name = "author")
    val Author: String,

    @ColumnInfo(name = "title")
    val Title: String,

    @ColumnInfo(name = "thumbnailURL")
    val thumbnailURL: String?,

    @ColumnInfo(name = "publish_date")
    val publishDate: String
)
