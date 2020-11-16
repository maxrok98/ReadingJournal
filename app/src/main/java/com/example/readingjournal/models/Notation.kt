package com.example.readingjournal.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "notation_table",
        foreignKeys = arrayOf(
            ForeignKey(entity = Book::class,
            parentColumns = arrayOf("Id"),
            childColumns = arrayOf("bookId"),
            onDelete = ForeignKey.CASCADE)
        ))
data class Notation(
    @PrimaryKey(autoGenerate = true)
    var Id: Long = 0L,

    @ColumnInfo(name = "bookId")
    var bookId: Long = 0L,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "text")
    var text: String,

    @ColumnInfo(name = "date")
    var date: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "likes")
    var likes: Long = 0
)
