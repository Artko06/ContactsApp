package com.example.contactsapp.presentation.screen.contactList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.domain.useCase.GetContactsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val getContactsUseCase: GetContactsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ContactListState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ContactListEffect>()
    val effect = _effect.asSharedFlow()

    fun onAction(action: ContactListAction) {
        when (action) {
            is ContactListAction.LoadContactsAction -> {
                loadContacts()
            }

            is ContactListAction.CallToContactAction -> {
                makeCall(phoneNumber = action.phoneNumber)
            }
        }
    }

    private fun loadContacts() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                val contacts = getContactsUseCase()

                _state.update {
                    it.copy(
                        isLoading = false,
                        contacts = contacts
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage ?: "Unknown error"
                    )
                }
                _effect.emit(
                    ContactListEffect.ShowErrorEffect(
                        message = e.localizedMessage ?: "Failed to load contacts"
                    )
                )
            }
        }
    }

    private fun makeCall(phoneNumber: String) {
        viewModelScope.launch {
            _effect.emit(
                ContactListEffect.MakeCallEffect(
                    phoneNumber = phoneNumber
                )
            )
        }
    }
}
