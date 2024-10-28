package com.example.e_book.ui_layer.tabSetUp


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.NavHostController
import com.example.e_book.Navigation.routes
import com.example.e_book.user_praf.UserPreferncesManager
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)

@Composable
fun Tabs(navController: NavHostController,userPreferncesManager : UserPreferncesManager) {

    val tabs = listOf(
        TabItem("Category", Icons.Rounded.Category),
        TabItem("Books", Icons.Rounded.Book),
        TabItem("BookMarks", Icons.Rounded.Bookmarks),
    )
    val pagerState = rememberPagerState(
        pageCount = { tabs.size }
    )

    val Socped = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth(),
        ) {
            tabs.fastForEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        Socped.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = tab.name) },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = null
                        )
                    })
            }

        }
        HorizontalPager(state = pagerState) {
            val Bookid by userPreferncesManager.BookId.collectAsState(initial = null)
            when (it) {
                0 -> CategoryScreen(navController = navController)
                1 -> AllBooksScreen(navController = navController)
                2 -> bookmarks(bookId = Bookid.toString(),navController = navController,userPreferncesManager = userPreferncesManager)
            }
        }
    }
}


data class TabItem(
    val name: String,
    val icon: ImageVector
)