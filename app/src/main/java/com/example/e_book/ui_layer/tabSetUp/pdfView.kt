package com.example.e_book.ui_layer.tabSetUp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader

import androidx.compose.material.*
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_book.Navigation.routes
import com.example.e_book.user_praf.UserPreferncesManager


import com.rizzi.bouquet.rememberVerticalPdfReaderState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pdfView(pdfUrl: String,bookid:String, bookname: String,navController: NavController,userPreferncesManager: UserPreferncesManager) {

    val context = LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(68.dp)
                    .background(color = MaterialTheme.colorScheme.primary),
                title = { Text(bookname) },

                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(routes.home)
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        GlobalScope.launch {
                            bookmark(userPreferncesManager = UserPreferncesManager(context),
                                bookid = bookid
                            )
                        }
                        /*bookmark(userPreferncesManager = UserPreferncesManager(context),
                            bookid = bookid
                        )*/


                        Log.d("TAG", "pdfView: $bookid")
                        Toast.makeText(context, "BookMark Added", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.Bookmarks, contentDescription = "BookMarks")
                    }
                }
            )
        },
        content = { innerPadding ->
            Spacer(modifier = Modifier.padding(20.dp))
            pdf(pdfUrl = pdfUrl, innerPadding = innerPadding)


        }
    )
}


@Composable
fun pdf(pdfUrl: String, innerPadding: PaddingValues) {
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Remote(pdfUrl),
        isZoomEnable = true
    )

    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )

}



suspend fun bookmark(userPreferncesManager: UserPreferncesManager, bookid:String)
{
    userPreferncesManager.saveBookId(bookid)
}