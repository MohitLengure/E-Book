package com.example.e_book.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.e_book.ui_layer.tabSetUp.AllBooksScreen
import com.example.e_book.ui_layer.tabSetUp.BookbyCategory
import com.example.e_book.ui_layer.tabSetUp.Tabs
import com.example.e_book.ui_layer.tabSetUp.bookmarks
import com.example.e_book.ui_layer.tabSetUp.pdfView
import com.example.e_book.user_praf.UserPreferncesManager
import com.example.e_book.viewModel.AppViewModel

@Composable
fun myapp(
    userPreferncesManager : UserPreferncesManager
)
{

     val navController = rememberNavController()
    val appViewModel: AppViewModel = hiltViewModel()

    val Bookid by userPreferncesManager.BookId.collectAsState(initial = null)

    NavHost(navController = navController, startDestination = routes.home) {

        composable<routes.home> {
            Tabs(navController,userPreferncesManager=userPreferncesManager)
        }



        composable<routes.pdfView> {
            val data = it.toRoute<routes.pdfView>()
            pdfView(pdfUrl = data.pdfUrl, bookname = data.bookname, bookid=data.bookId, navController = navController, userPreferncesManager = userPreferncesManager)
        }
        composable<routes.bookByCategory> {
            val data = it.toRoute<routes.bookByCategory>()
            BookbyCategory(navController = navController, category = data.categoryName)
        }
        composable<routes.bookByID> {
            bookmarks(navController = navController, bookId = Bookid.toString(), userPreferncesManager = userPreferncesManager)
        }


    }

}