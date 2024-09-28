package com.develop.journalapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.develop.journalapp.R
import com.develop.journalapp.databinding.FragmentJournalListBinding
import com.google.firebase.auth.FirebaseAuth

class JournalListFragment : Fragment(),MenuProvider {

    private lateinit var jListBind : FragmentJournalListBinding
    private var fbAuth : FirebaseAuth? = null

    private lateinit var navOptions : NavOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        jListBind = DataBindingUtil.inflate(inflater, R.layout.fragment_journal_list,container,false)
        return jListBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Set up the toolbar as an ActionBar
        (activity as AppCompatActivity).setSupportActionBar(jListBind.toolbar)

        fbAuth = FirebaseAuth.getInstance()
        navOptions = NavOptions.Builder().setPopUpTo(R.id.journalListFragment,true).build()
        requireActivity().addMenuProvider(this,viewLifecycleOwner)
        jListBind.lifecycleOwner = viewLifecycleOwner

        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.my_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.action_add -> {
                true
            }
            R.id.action_signout -> {
                fbAuth?.signOut()
                if(fbAuth?.currentUser == null){
                    findNavController().navigate(R.id.action_journalListFragment_to_loginFragment,null,navOptions)
                }
                true
            }
            else -> false
        }
    }


}