package com.example.jetpackcomposedemo

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Contact(
    val id: String,
    val name: String,
    val contactNumber: String,
    val cardColor: Color
)

class MainViewModel : ViewModel() {
    private val _contactList = MutableLiveData<List<Contact>>()
    val contactList: LiveData<List<Contact>> get() = _contactList

    init {
        addContact(
            Contact(
                "1", "Hello", "0949581608",
                Color.Yellow
            )
        )
    }

    fun addContact(contact: Contact) {
        var list = _contactList.value.orEmpty().toMutableList()
        _contactList.value = list + contact
    }
}