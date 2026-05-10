package com.example.contactsapp.di

import com.example.contactsapp.data.dataSource.local.ContactsLocalDataSource
import com.example.contactsapp.data.dataSource.local.ContactsLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindContactsLocalDataSource(
        impl: ContactsLocalDataSourceImpl
    ): ContactsLocalDataSource
}