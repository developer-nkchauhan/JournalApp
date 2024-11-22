package com.develop.journalapp.di.module

import com.develop.journalapp.model.Constant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideCollectionReference(): CollectionReference {
        val firestore = FirebaseFirestore.getInstance()
        return firestore.collection(Constant.COLLECTION_REFER) // Replace with your collection name
    }
}