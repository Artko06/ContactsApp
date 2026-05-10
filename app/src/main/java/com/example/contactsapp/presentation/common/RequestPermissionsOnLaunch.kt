package com.example.contactsapp.presentation.common

import android.Manifest
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun RequestPermissionsOnLaunch(
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
) {
    LaunchedEffect(Unit) {
        launcher.launch(
            arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CALL_PHONE
            )
        )
    }
}