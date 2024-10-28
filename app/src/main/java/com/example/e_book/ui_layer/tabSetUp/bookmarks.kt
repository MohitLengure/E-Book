package com.example.e_book.ui_layer.tabSetUp

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_book.user_praf.UserPreferncesManager
import com.example.e_book.viewModel.AppViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun bookmarks(viewModel: AppViewModel = hiltViewModel(),bookId:String,navController: NavController,userPreferncesManager : UserPreferncesManager
) {
    val Bookid by userPreferncesManager.BookId.collectAsState(initial = null)

    val state = viewModel.getBooksByID.collectAsState()
    val data = state.value.data ?: emptyList()
    val context = LocalContext.current



    Box(modifier = Modifier.fillMaxSize())
    {

        LaunchedEffect(Bookid) {
            if (Bookid != null) {
                viewModel.getBooksbyId(bookId)
                Log.d("BookId", "$bookId")
            } else {
                Toast.makeText(context, "No Book Found", Toast.LENGTH_SHORT).show()
            }
        }


        ;
        /* LaunchedEffect(key1 = Unit){
            viewModel.getBooksbyId(bookId)
            Log.d("BookId","$bookId")
        }*/

        when {
            state.value.isLoading -> {
                CircularProgressIndicator()
            }

            state.value.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.value.error.toString())
                }
            }

            state.value.data.isNotEmpty() -> {
                LazyColumn {
                    items(data)
                    {
                        books(
                            title = it.bookName,
                            url = it.bookUrl,
                            bookImage = it.bookImage,
                            author = it.bookAuthor,
                            bookid = it.bookId,
                            onItemClicked = {
                                navController.navigate(com.example.e_book.Navigation.routes.pdfView(it.bookUrl,it.bookName,it.bookId))
                            }
                        )
                    }
                }
            }
        }
        Button(modifier = Modifier.align(Alignment.BottomCenter),
            onClick = {
                GlobalScope.launch {
                    clearbutton(userPreferncesManager = UserPreferncesManager(context))
                }
            }) {
            Text(text = "Clear")
        }
    }
}

suspend fun clearbutton(userPreferncesManager: UserPreferncesManager) = userPreferncesManager.clearBookId()

