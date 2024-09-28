package com.develop.journalapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.develop.journalapp.databinding.FragmentAddJournalBinding

class AddJournalFragment : Fragment() {

    private lateinit var addJournalBinding: FragmentAddJournalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addJournalBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_journal,container,false)
        return addJournalBinding.root
    }
}