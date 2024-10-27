package com.example.e_book.ui_layer.tabSetUp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.e_book.Navigation.routes
import com.example.e_book.viewModel.AppViewModel


@Composable
fun CategoryScreen(viewModel: AppViewModel = hiltViewModel(),navController: NavController) {

    val state = viewModel.getAllCategoryState.collectAsState()
    val context = LocalContext.current

    val data = state.value.data ?: emptyList()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllCategory()
    }

    when {
        state.value.isLoading -> {
            Text(text = "Loading")
        }

        state.value.error != null -> {
            Toast.makeText(context, state.value.error.toString(), Toast.LENGTH_SHORT).show()
        }

        state.value.data.isNotEmpty() -> {

            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(data)
                    {
                        Box(
                            modifier = Modifier // Adjust height as needed
                        ) {
                            AsyncImage(
                                model = it.categoryImageUrl ,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop // Adjust as needed
                            )
                            Text(
                                text = it.name,
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate(routes.bookByCategory(it.name))
                                    }
                                    .align(Alignment.Center) // Position the text at the center
                                    .background(Color.Black.copy(alpha = 0.8f)) // Semi-transparent background
                                    .padding(8.dp), // Padding around the text
                                color = Color.White // Text color
                            )
                        }

                       /* Text(text = it.name, modifier = Modifier.clickable {
                            navController.navigate(routes.bookByCategory(it.name))
                        })*/
                    }

                }

            }
        }
    }
}

