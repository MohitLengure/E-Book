package com.example.e_book.di

import com.example.e_book.data.repo.Repo
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModel {

    @Provides
    @Singleton
    fun provideFirebaseRealtimeDatabase() :FirebaseDatabase{
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideRepo(firebaseDatabase: FirebaseDatabase): Repo {
        return Repo(firebaseDatabase)

    }

}