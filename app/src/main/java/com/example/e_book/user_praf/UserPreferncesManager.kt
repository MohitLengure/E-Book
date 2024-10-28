package com.example.e_book.user_praf

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore("book_preferences")
class UserPreferncesManager(private val context: Context) {

    companion object
    {
        private val Book_ID_KEY = stringPreferencesKey("bookId")
    }


    suspend fun saveBookId(BookId:String)
    {
        context.dataStore.edit {
            it[Book_ID_KEY] = BookId
            //println("BookID: ${it[Book_ID_KEY]}").toString()
        }
    }

    val BookId : Flow<String?> = context.dataStore.data.map {
        it[Book_ID_KEY]
        //println("BookId: ${it[Book_ID_KEY]}").toString()
    }

    suspend fun clearBookId()
    {
        context.dataStore.edit {
            it.remove(Book_ID_KEY)
        }
    }

}