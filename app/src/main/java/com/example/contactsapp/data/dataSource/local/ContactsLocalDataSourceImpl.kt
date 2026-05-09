package com.example.contactsapp.data.dataSource.local

import android.content.Context
import android.provider.ContactsContract
import com.example.contactsapp.data.entity.ContactDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactsLocalDataSourceImpl(
    private val context: Context
): ContactsLocalDataSource {

    override suspend fun getContacts(): List<ContactDataEntity> = withContext(Dispatchers.IO) {
        val contactsList = mutableListOf<ContactDataEntity>()

        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.IS_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.IS_SUPER_PRIMARY
        )

        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        cursor?.use {
            val idIndex = it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val isPrimaryIndex = it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.IS_PRIMARY)
            val isSuperPrimaryIndex = it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.IS_SUPER_PRIMARY)

            while (it.moveToNext()) {
                val id = it.getString(idIndex)
                val name = it.getString(nameIndex) ?: ""
                val number = it.getString(numberIndex) ?: ""

                val isPrimary = it.getInt(isPrimaryIndex) != 0
                val isSuperPrimary = it.getInt(isSuperPrimaryIndex) != 0

                if (number.isNotBlank()) {
                    contactsList.add(
                        ContactDataEntity(
                            id = id,
                            name = name,
                            number = number,
                            isSuperPrimary = isSuperPrimary,
                            isPrimary = isPrimary
                        )
                    )
                }
            }
        }

        contactsList
    }
}