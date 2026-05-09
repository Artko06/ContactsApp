package com.example.contactsapp.data.repository

import com.example.contactsapp.data.dataSource.local.ContactsLocalDataSource
import com.example.contactsapp.domain.entity.ContactItem
import com.example.contactsapp.domain.repository.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactsRepositoryImpl(
    private val contactsLocalDataSource: ContactsLocalDataSource
) : ContactsRepository {

    override suspend fun getContacts(): List<ContactItem> = withContext(Dispatchers.Default) {
        val dataEntities = contactsLocalDataSource.getContacts()
        val groupedContacts = dataEntities.groupBy { it.id }

        groupedContacts.map { (id, variations) ->
            val mainPhoneEntity = variations.firstOrNull { it.isSuperPrimary }
                ?: variations.firstOrNull { it.isPrimary }
                ?: variations.first()

            ContactItem(
                id = id,
                displayName = mainPhoneEntity.name,
                phoneNumber = mainPhoneEntity.number
            )
        }.sortedBy { it.displayName }
    }
}