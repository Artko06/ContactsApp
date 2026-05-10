package com.example.contactsapp.presentation.common

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import com.example.contactsapp.R
import com.example.contactsapp.presentation.screen.contactList.ContactListEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun HandleContactListEffects(
    effectFlow: Flow<ContactListEffect>,
    context: Context
) {
    val noCallPermStr = stringResource(R.string.no_call_permission)

    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                is ContactListEffect.MakeCallEffect -> {
                    try {
                        val callIntent = Intent(Intent.ACTION_CALL).apply {
                            data = "tel:${effect.phoneNumber}".toUri()
                        }
                        context.startActivity(callIntent)
                    } catch (e: SecurityException) {
                        Toast.makeText(context, noCallPermStr, Toast.LENGTH_SHORT).show()
                    }
                }

                is ContactListEffect.ShowErrorEffect -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}