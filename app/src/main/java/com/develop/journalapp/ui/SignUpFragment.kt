package com.develop.journalapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.develop.journalapp.R
import com.develop.journalapp.databinding.FragmentSignUpBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var signUpBinding : FragmentSignUpBinding
    @Inject lateinit var fbAuth : FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signUpBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up,container,false)
        return signUpBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        fbAuth = FirebaseAuth.getInstance()

        signUpBinding.accSignUpBtn.setOnClickListener {
            val email = signUpBinding.emailCreate.text.trim().toString()
            val password = signUpBinding.passwordCreate.text.trim().toString()
            val username = signUpBinding.usernameCreateET.text.trim().toString()

            if(email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
                signUpUser(email,password,username)
            }

        }
    }

    private fun signUpUser(email: String, password: String, username: String) {
            fbAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task : Task<AuthResult> ->
                    if(task.isSuccessful){
                        Toast.makeText(signUpBinding.root.context, "Registered Successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(signUpBinding.root.context, "${exception.message}", Toast.LENGTH_SHORT).show()
                }
    }
}