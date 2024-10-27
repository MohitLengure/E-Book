package com.example.e_book.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.e_book.ui_layer.tabSetUp.AllBooksScreen
import com.example.e_book.ui_layer.tabSetUp.BookbyCategory
import com.example.e_book.ui_layer.tabSetUp.Tabs
import com.example.e_book.ui_layer.tabSetUp.pdfView

@Composable
fun myapp()
{

     val navController = rememberNavController()

    NavHost(navController = navController, startDestination = routes.home) {

        composable<routes.home> {
            Tabs(navController)
        }
        composable<routes.pdfView> {
            val data = it.toRoute<routes.pdfView>()
            pdfView(pdfUrl = data.pdfUrl)
        }
        composable<routes.bookByCategory> {
            val data = it.toRoute<routes.bookByCategory>()
            BookbyCategory(navController = navController, category = data.categoryName)
        }


    }

}