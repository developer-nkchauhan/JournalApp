package com.develop.journalapp.model

import com.google.firebase.Timestamp

data class Journal(
    val title : String="",
    val thoughts : String="",
    val imageUrl : String="",
    val userId: String="",
    val timeAdded : Timestamp= Timestamp.now(),
    val userName : String=""
)