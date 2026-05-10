package com.example.contactsapp.presentation.screen.contactList

import com.example.contactsapp.domain.entity.ContactItem

data class ContactListState(
    val contacts: List<ContactItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
