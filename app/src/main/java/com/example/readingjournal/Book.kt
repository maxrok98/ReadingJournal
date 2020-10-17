package com.example.readingjournal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Book (val Author: String, val Title: String, val notations: MutableList<Notation> = mutableListOf()) :
    Parcelable {
}