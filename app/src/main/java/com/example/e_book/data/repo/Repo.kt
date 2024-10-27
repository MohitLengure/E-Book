package com.example.e_book.data.repo

import com.example.e_book.ResultState
import com.example.e_book.data.response.BookCategoryModels
import com.example.e_book.data.response.BookModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose

class Repo @Inject constructor(private val firebaseDatabase: FirebaseDatabase) {

    suspend fun getALlBooks(): Flow<ResultState<List<BookModels>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookModels> = emptyList()

                items = snapshot.children.map { value ->
                    value.getValue<BookModels>()!!
                }

                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }


        }

        firebaseDatabase.reference.child("Books").addValueEventListener(
            valueEvent
        )

        awaitClose {
            firebaseDatabase.reference.child("Books").removeEventListener(valueEvent)
        }

    }


    suspend fun getALlCategory(): Flow<ResultState<List<BookCategoryModels>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookCategoryModels> = emptyList()

                items = snapshot.children.map { value ->
                    value.getValue<BookCategoryModels>()!!
                }

                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }
        }
        firebaseDatabase.reference.child("BookCategory").addValueEventListener(
            valueEvent
        )
        awaitClose {
            firebaseDatabase.reference.child("BookCategory").removeEventListener(valueEvent)
        }
    }


    suspend fun getBookByCategory(category: String): Flow<ResultState<List<BookModels>>> = callbackFlow {

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookModels> = emptyList()
                items  = snapshot.children.filter {
                    it.getValue<BookModels>()!!.category == category
                }.map {
                    it.getValue<BookModels>()!!

                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }


        }

        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)
        awaitClose {
            firebaseDatabase.reference.child("Books").removeEventListener(valueEvent)
        }


    }
}