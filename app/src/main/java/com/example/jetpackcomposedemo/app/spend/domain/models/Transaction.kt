package com.example.jetpackcomposedemo.app.spend.domain.models

data class Transaction(
    val id: String? = "",
    val contact: Contact,
)

data class Contact(
    val id: String? = "",
    val name: String,
    val bankName: String? = "",
    val bankAccount: String? = ""
)