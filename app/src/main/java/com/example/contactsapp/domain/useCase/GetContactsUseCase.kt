package com.example.contactsapp.domain.useCase

import com.example.contactsapp.domain.entity.ContactItem
import com.example.contactsapp.domain.repository.ContactsRepository
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository
) {
    suspend operator fun invoke(): List<ContactItem> {
        return contactsRepository.getContacts()
    }
}