package com.example.readingjournal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Notation(val title: String, val text: String, val date: Date = Calendar.getInstance().time) :
    Parcelable
