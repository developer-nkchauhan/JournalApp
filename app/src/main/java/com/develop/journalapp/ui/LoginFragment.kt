package com.develop.journalapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.develop.journalapp.R
import com.develop.journalapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var loginBind : FragmentLoginBinding

    @Inject lateinit var fbAuth : FirebaseAuth
    private lateinit var fbAuthListener : FirebaseAuth.AuthStateListener
    private var currentUser : FirebaseUser? = null

    private lateinit var navOptions : NavOptions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginBind = DataBindingUtil.inflate(inflater, R.layout.fragment_login,container,false)
        return loginBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navOptions = NavOptions.Builder().setPopUpTo(R.id.loginFragment,true).build()

//        fbAuthListener = FirebaseAuth.AuthStateListener { it ->
//            currentUser = fbAuth?.currentUser
//        }
        if(fbAuth.currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_journalListFragment,null,navOptions)
        }

        loginBind.creatAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        loginBind.btnSignIn.setOnClickListener {
            val email = loginBind.email.text.trim().toString()
            val password = loginBind.password.text.trim().toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                doLogin(email,password)
            }
        }
    }

    private fun doLogin(email: String, password: String) {
        fbAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    findNavController().navigate(R.id.action_loginFragment_to_journalListFragment,null,navOptions)
                }
            }
            .addOnFailureListener {
                Toast.makeText(loginBind.root.context, "${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}