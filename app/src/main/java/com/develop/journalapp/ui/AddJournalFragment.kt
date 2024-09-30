package com.develop.journalapp

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.develop.journalapp.databinding.FragmentAddJournalBinding
import com.develop.journalapp.model.Constant
import com.develop.journalapp.model.Journal
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.Date

class AddJournalFragment : Fragment() {

    private lateinit var addJournalBinding: FragmentAddJournalBinding

    private lateinit var fbStorage : FirebaseStorage
    private lateinit var storageReference: StorageReference

    private lateinit var fbFireStore : FirebaseFirestore


    private lateinit var fbAuth : FirebaseAuth
    private lateinit var fbCollRef : CollectionReference
    private var user : FirebaseUser? = null
    private var imgUrl : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addJournalBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_journal,container,false)
        return addJournalBinding.root
    }

    private val imageSelectLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){ it ->
        addJournalBinding.postImageView.setImageURI(it)
        imgUrl = it
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fbAuth = FirebaseAuth.getInstance()
        fbFireStore = FirebaseFirestore.getInstance()
        fbCollRef = fbFireStore.collection(Constant.COLLECTION_REFER)

        fbStorage = FirebaseStorage.getInstance()
        storageReference = fbStorage.reference

        addJournalBinding.postCameraButton.setOnClickListener {
            imageSelectLauncher.launch("image/*")

        }

        addJournalBinding.postSaveJournalButton.setOnClickListener {
//            val imgUrl = addJournalBinding.postImageView.
            val postTitle = addJournalBinding.postTitleEt.text.toString()
            val postThoughts = addJournalBinding.postDescriptionEt.text.toString()

            if(postTitle.isNotEmpty()
                && postThoughts.isNotEmpty()
                && imgUrl.toString().isNotEmpty()
            ){
                addJournalBinding.postProgressBar.visibility = View.VISIBLE
                saveJournalToFBStorage(imgUrl,postTitle,postThoughts)
            }else{
                Toast.makeText(addJournalBinding.root.context, "Values can't be empty..", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveJournalToFBStorage(imgUrl: Uri?, postTitle: String, postThoughts: String) {
        val filepath : StorageReference = storageReference.child("journal_images")
            .child("my_image_${Timestamp.now().seconds}")

        filepath.putFile(imgUrl!!).addOnSuccessListener {
             filepath.downloadUrl.addOnSuccessListener { dwnImageUrl ->
                 val journal = Journal(
                     postTitle,
                     postThoughts,
                     dwnImageUrl.toString(),
                     fbAuth.uid.toString(),
                     Timestamp(Date()),
                     user.toString(),
                 )

                 fbCollRef.add(journal).addOnSuccessListener {
                     addJournalBinding.postProgressBar.visibility = View.GONE
                     Toast.makeText(addJournalBinding.root.context.applicationContext, "Added Successfully", Toast.LENGTH_SHORT).show()
                     findNavController().navigate(R.id.action_addJournalFragment_to_journalListFragment)
                 }
                 .addOnFailureListener {
                     Toast.makeText(
                         addJournalBinding.root.context.applicationContext,
                         "Failed to save in database",
                         Toast.LENGTH_SHORT
                     ).show()
                 }
             }
        }
        .addOnFailureListener {
            Toast.makeText(
                addJournalBinding.root.context.applicationContext,
                "Failed to load image url",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onStart() {
        super.onStart()
        user = fbAuth.currentUser
    }
}