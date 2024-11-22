package com.develop.journalapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.develop.journalapp.IProgressIndicator
import com.develop.journalapp.R
import com.develop.journalapp.databinding.ActivityMainBinding
import com.develop.journalapp.databinding.CustomProgressBarBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),IProgressIndicator {

    private lateinit var mainBind : ActivityMainBinding

    private lateinit var dialog : AlertDialog
    private lateinit var customProBarBind : CustomProgressBarBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        customProBarBind = CustomProgressBarBinding.inflate(LayoutInflater.from(this),null,false)
        dialog = AlertDialog.Builder(this).setView(customProBarBind.root).setCancelable(false).setTitle("Please Wait!").create()
    }

    override fun showProgressBar(msg: String) {
        customProBarBind.progressMsg.text = msg
        dialog.show()
    }

    override fun dismissProgressBar() {
        if(dialog.isShowing){
            dialog.dismiss()
        }
    }


}