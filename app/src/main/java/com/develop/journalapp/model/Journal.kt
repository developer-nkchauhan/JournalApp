package com.develop.journalapp.model

import com.google.firebase.Timestamp

data class Journal(
    private val title : String,
    private val thoughts : String,
    private val imageUrl : String,
    private val userId: String,
    private val timeAdded : Timestamp,
    private val userName : String
)
