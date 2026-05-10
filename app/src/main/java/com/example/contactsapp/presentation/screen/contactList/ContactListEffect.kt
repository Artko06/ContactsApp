package com.example.contactsapp.presentation.screen.contactList

sealed interface ContactListEffect {
    data class MakeCallEffect(val phoneNumber: String) : ContactListEffect
    data class ShowErrorEffect(val message: String) : ContactListEffect
}