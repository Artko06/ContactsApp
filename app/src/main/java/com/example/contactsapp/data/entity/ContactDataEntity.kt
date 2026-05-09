package com.example.contactsapp.data.entity

data class ContactDataEntity(
    val id: String,
    val name: String,
    val number: String,
    val isSuperPrimary: Boolean,
    val isPrimary: Boolean
)
