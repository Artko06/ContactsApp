package com.example.contactsapp.presentation.screen.contactList.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.contactsapp.R
import com.example.contactsapp.presentation.common.HandleContactListEffects
import com.example.contactsapp.presentation.common.RequestPermissionsOnLaunch
import com.example.contactsapp.presentation.common.rememberContactPermissionLauncher
import com.example.contactsapp.presentation.screen.contactList.ContactListAction
import com.example.contactsapp.presentation.screen.contactList.ContactListState
import com.example.contactsapp.presentation.screen.contactList.ContactListViewModel
import com.example.contactsapp.presentation.screen.contactList.ui.component.ContactItemView

@Composable
fun ContactListScreen(contactListViewModel: ContactListViewModel) {
    val context = LocalContext.current
    val state = contactListViewModel.state.collectAsStateWithLifecycle().value

    val permissionLauncher = rememberContactPermissionLauncher(
        viewModel = contactListViewModel,
        context = context
    )
    RequestPermissionsOnLaunch(permissionLauncher)

    HandleContactListEffects(
        effectFlow = contactListViewModel.effect,
        context = context
    )

    ContactListView(
        contactListState = state,
        onContactClick = { phoneNumber ->
            contactListViewModel.onAction(
                ContactListAction.CallToContactAction(phoneNumber)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListView(
    contactListState: ContactListState,
    onContactClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.title_contact_list))
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                contactListState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                contactListState.error != null -> {
                    Text(
                        text = contactListState.error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                contactListState.contacts.isEmpty() -> {
                    Text(
                        text = stringResource(R.string.contacts_not_found),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(
                            items = contactListState.contacts,
                            key = { it.id }
                        ) { contact ->
                            ContactItemView(
                                contactItem = contact,
                                onClick = { onContactClick(contact.phoneNumber) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun ContactListViewPreview() {
    ContactListView(
        contactListState = ContactListState(),
        onContactClick = { }
    )
}