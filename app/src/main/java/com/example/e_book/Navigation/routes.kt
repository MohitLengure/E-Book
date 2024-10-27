package com.example.e_book.Navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class routes {

    @Serializable
    object home

    @Serializable
    data class pdfView(
        val pdfUrl: String
    )

    @Serializable
    data class bookByCategory
        (val categoryName: String, )
}