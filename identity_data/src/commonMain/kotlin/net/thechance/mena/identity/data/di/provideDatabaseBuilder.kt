package net.thechance.mena.identity.data.di

import androidx.room.RoomDatabase
import org.example.project.data.database.IdentityDatabase
import org.koin.core.scope.Scope

expect fun Scope.provideDatabaseBuilder(): RoomDatabase.Builder<IdentityDatabase>