package com.example.contactsapp.presentation.common

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.contactsapp.R
import com.example.contactsapp.presentation.screen.contactList.ContactListAction
import com.example.contactsapp.presentation.screen.contactList.ContactListViewModel

@Composable
fun rememberContactPermissionLauncher(
    viewModel: ContactListViewModel,
    context: Context
): ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>> {
    val permissionRequiredMessage = stringResource(R.string.permission_read_contacts_required)

    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.READ_CONTACTS] == true) {
            viewModel.onAction(ContactListAction.LoadContactsAction)
        } else {
            Toast.makeText(context, permissionRequiredMessage, Toast.LENGTH_LONG).show()
        }
    }
}