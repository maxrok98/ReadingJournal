package com.example.readingjournal.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "books_table")
data class Book (
    @PrimaryKey(autoGenerate = true)
    var Id: Long = 0L,

    @ColumnInfo(name = "author")
    val Author: String,

    @ColumnInfo(name = "title")
    val Title: String,

    @ColumnInfo(name = "thumbnailURL")
    val thumbnailURL: String?
)

