package com.misenpai.anivault.core.di

import android.app.Application
import androidx.room.Room
import com.misenpai.anivault.data.local.dao.AnimeDao
import com.misenpai.anivault.data.local.dao.UserDao
import com.misenpai.anivault.data.local.database.AniVaultDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{
    @Provides
    @Singleton
    fun provideAniVaultDatabase(app: Application): AniVaultDatabase{
        return Room.databaseBuilder(
            app,
            AniVaultDatabase::class.java,
            "anivault_database"
        ).fallbackToDestructiveMigration().build()
    }
    @Provides
    @Singleton
    fun provideUserDao(database: AniVaultDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideAnimeDao(database: AniVaultDatabase): AnimeDao = database.animeDao()
}