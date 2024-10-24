package com.develop.journalapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.develop.journalapp.R
import com.develop.journalapp.adapter.JournalAdapter
import com.develop.journalapp.databinding.FragmentJournalListBinding
import com.develop.journalapp.model.Constant
import com.develop.journalapp.model.Journal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class JournalListFragment : Fragment(),MenuProvider {

    private lateinit var jListBind : FragmentJournalListBinding
    private var fbAuth : FirebaseAuth? = null
    /**
     *  Maintaining it to compare that Firebase data changed or not
     *  If changed then refresh adapter by adding only new item not all recyclerview data.
     */
    private var journalList = mutableListOf<Journal>()

    private lateinit var navOptions : NavOptions

    private lateinit var fbFireStore : FirebaseFirestore
    private lateinit var collectionReference: CollectionReference

    private var adapter : JournalAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        jListBind = DataBindingUtil.inflate(inflater, R.layout.fragment_journal_list,container,false)
        return jListBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {

            // Set up the toolbar as an ActionBar
            (activity as AppCompatActivity).setSupportActionBar(jListBind.toolbar)

            fbAuth = FirebaseAuth.getInstance()
            fbFireStore = FirebaseFirestore.getInstance()
            collectionReference = fbFireStore.collection(Constant.COLLECTION_REFER)

            collectionReference.addSnapshotListener { value, error ->
                if(error != null){
                    Toast.makeText(this.requireContext(), error.message, Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                if(value?.isEmpty == false){
                    if(journalList.size < value.size()){
                        for(data in value.documents){
                            val journal = data.toObject(Journal::class.java)
                            if(!journalList.contains(journal)){
                                journalList.add(journal!!)
                                adapter?.addData(journal)
                            }
                        }
                    }else{
                        adapter?.updateRecyclerView(journalList)
                    }
                }
            }
//            adapter?.updateRecyclerView(journalList)

            navOptions = NavOptions.Builder().setPopUpTo(R.id.journalListFragment,true).build()
            requireActivity().addMenuProvider(this,viewLifecycleOwner)
            jListBind.lifecycleOwner = viewLifecycleOwner

    //        jListBind.recyclerView.adapter = adapter
            adapter = JournalAdapter(jListBind.root.context)
            jListBind.recyclerView.adapter = adapter
            jListBind.recyclerView.layoutManager = LinearLayoutManager(requireContext())

//            retrieveDataAndDisplay()
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

//    private fun retrieveDataAndDisplay() {
//        collectionReference.get().addOnSuccessListener { querySnapshots ->
//            for(doc in querySnapshots) {
//                val journal = doc.toObject(Journal::class.java)
//                if(journal.userId == fbAuth?.currentUser?.uid){
////                    journalList.add(journal)
//                    adapter?.addData(journal)
//                }
//            }
//        }
//        .addOnFailureListener {
//            Toast.makeText(jListBind.root.context, "Failed to set data in recyclerview", Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.my_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.action_add -> {
                findNavController().navigate(R.id.action_journalListFragment_to_addJournalFragment)
                true
            }
            R.id.action_signout -> {
                AlertDialog.Builder(this.requireContext())
                    .setTitle("Log Out")
                    .setMessage("Are you sure you want to Logout ?")
                    .setCancelable(false)
                    .setPositiveButton("Logout"){ dialog,_ ->
                        fbAuth?.signOut()
                        if(fbAuth?.currentUser == null){
                            findNavController().navigate(R.id.action_journalListFragment_to_loginFragment,null,navOptions)
                        }
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel"){ dialog,_ ->
                        dialog.dismiss()
                    }
                    .show()
//                fbAuth?.signOut()

                true
            }
            else -> false
        }
    }
}