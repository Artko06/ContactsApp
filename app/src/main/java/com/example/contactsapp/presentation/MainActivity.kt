package com.example.contactsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.contactsapp.presentation.screen.contactList.ContactListViewModel
import com.example.contactsapp.presentation.screen.contactList.ui.ContactListScreen
import com.example.contactsapp.presentation.ui.theme.ContactsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactsAppTheme {
                val contactListViewModel: ContactListViewModel = hiltViewModel()

                ContactListScreen(
                    contactListViewModel = contactListViewModel
                )
            }
        }
    }
}