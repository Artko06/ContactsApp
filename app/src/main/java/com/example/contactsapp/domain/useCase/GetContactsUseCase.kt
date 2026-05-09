package com.example.contactsapp.domain.useCase

import com.example.contactsapp.domain.entity.ContactItem
import com.example.contactsapp.domain.repository.ContactsRepository

class GetContactsUseCase(
    private val contactsRepository: ContactsRepository
) {
    suspend operator fun invoke(): List<ContactItem> {
        return contactsRepository.getContacts()
    }
}