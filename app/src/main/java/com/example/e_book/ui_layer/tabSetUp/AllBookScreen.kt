package com.example.e_book.ui_layer.tabSetUp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_book.viewModel.AppViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.e_book.Navigation.routes


@Composable
fun AllBooksScreen(viewModel: AppViewModel = hiltViewModel(), navController: NavController) {
    val state = viewModel.getAllBooksState.collectAsState()
    val data = state.value.data ?: emptyList()


    LaunchedEffect(key1 = Unit) {
        viewModel.getAllBooks()
    }


    when {
        state.value.isLoading -> {

        }

        state.value.error != null -> {

        }

        state.value.data != null -> {

            Column(
                modifier = Modifier.fillMaxWidth()
            )
            {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(data) {
                        books(
                            bookid=it.bookId,
                            title = it.bookName,
                            url = it.bookUrl,
                            bookImage = it.bookImage,
                            author = it.bookAuthor,
                            onItemClicked ={
                                navController.navigate(routes.pdfView(it.bookUrl,it.bookName,it.bookId))
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun books(
    bookid:String,
    title: String,
    url: String,
    bookImage: String,
    author: String,
    onItemClicked: () -> Unit = {}
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClicked() })
    {
        Row {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize(0.5f)
            ) {
                AsyncImage(
                    model = bookImage,
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Adjust as needed
                )

            }
            Column()
            {
                Text(text = "Title :- $title", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "Author $author",fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

        }
    }
    Spacer(modifier = Modifier.padding(10.dp))
}


