package com.example.contactsapp.presentation.screen.contactList

sealed interface ContactListAction {
    object LoadContactsAction : ContactListAction
    data class CallToContactAction(val phoneNumber: String): ContactListAction
}