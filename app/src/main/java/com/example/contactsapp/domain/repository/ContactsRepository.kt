package com.example.contactsapp.domain.repository

import com.example.contactsapp.domain.entity.ContactItem

interface ContactsRepository {
    suspend fun getContacts(): List<ContactItem>
}