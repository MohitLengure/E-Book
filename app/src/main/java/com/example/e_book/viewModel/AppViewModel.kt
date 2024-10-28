package com.example.e_book.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_book.ResultState
import com.example.e_book.data.repo.Repo
import com.example.e_book.data.response.BookCategoryModels
import com.example.e_book.data.response.BookModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppViewModel @Inject constructor(private val repo: Repo) : ViewModel() {


    private val _getAllBooksState = MutableStateFlow(GetAllBooksState())
    val getAllBooksState = _getAllBooksState

    fun getAllBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getALlBooks().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getAllBooksState.value = GetAllBooksState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getAllBooksState.value =
                            GetAllBooksState(isLoading = false, data = it.data)
                    }

                    is ResultState.Error -> {
                        _getAllBooksState.value =
                            GetAllBooksState(isLoading = false, error = it.exception)
                    }
                }
            }
        }
    }


    private val _getAllCategoryState = MutableStateFlow(GetAllCategoryState())
    val getAllCategoryState = _getAllCategoryState

    fun getAllCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getALlCategory().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getAllCategoryState.value = GetAllCategoryState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getAllCategoryState.value =
                            GetAllCategoryState(isLoading = false, data = it.data)
                    }

                    is ResultState.Error -> {
                        _getAllCategoryState.value =
                            GetAllCategoryState(isLoading = false, error = it.exception)
                    }
                }
            }
        }
    }

    private val _getBoooksByCategoryStat = MutableStateFlow(GetBoooksByCategoryStat())
    val getBoooksByCategoryStat = _getBoooksByCategoryStat.asStateFlow()

    fun getBoooksByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getBookByCategory(category).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getBoooksByCategoryStat.value = GetBoooksByCategoryStat(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getBoooksByCategoryStat.value =
                            GetBoooksByCategoryStat(isLoading = false, data = it.data)
                    }

                    is ResultState.Error -> {
                        _getBoooksByCategoryStat.value =
                            GetBoooksByCategoryStat(isLoading = false, error = it.exception)
                    }
                }
            }

        }

    }

    private val _getBooksByID = MutableStateFlow(GetBoooksByID())
    val getBooksByID = _getBooksByID.asStateFlow()

    fun getBooksbyId(Bookid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getBooksbyId(Bookid).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getBooksByID.value = GetBoooksByID(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getBooksByID.value =
                            GetBoooksByID(isLoading = false, data = it.data)
                    }

                    is ResultState.Error -> {
                        _getBooksByID.value =
                            GetBoooksByID(isLoading = false, error = it.exception)
                    }
                }
            }

        }

    }


}

data class GetAllBooksState(
    val isLoading: Boolean = false,
    val data: List<BookModels> = emptyList(),
    val error: Throwable? = null

)
data class GetAllCategoryState(
    val isLoading: Boolean = false,
    val data: List<BookCategoryModels> = emptyList(),
    val error: Throwable? = null

)
data class GetBoooksByCategoryStat(
    val isLoading: Boolean = false,
    val data: List<BookModels> = emptyList(),
    val error: Throwable? = null
)

data class GetBoooksByID(
    val isLoading: Boolean = false,
    val data: List<BookModels> = emptyList(),
    val error: Throwable? = null
)