package com.develop.journalapp

interface IProgressIndicator {

    fun showProgressBar(msg : String)

    fun dismissProgressBar()
}