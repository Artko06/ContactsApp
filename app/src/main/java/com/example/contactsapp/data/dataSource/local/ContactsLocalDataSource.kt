package com.example.contactsapp.data.dataSource.local

import com.example.contactsapp.data.entity.ContactDataEntity

interface ContactsLocalDataSource {
    suspend fun getContacts(): List<ContactDataEntity>
}