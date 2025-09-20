package com.misenpai.anivault.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.misenpai.anivault.core.util.Converters
import com.misenpai.anivault.data.local.dao.AnimeDao
import com.misenpai.anivault.data.local.dao.UserDao
import com.misenpai.anivault.data.local.entities.AnimeStatusEntity
import com.misenpai.anivault.data.local.entities.UserEntity
import com.misenpai.anivault.domain.model.AnimeStatus

@Database(
    entities = [UserEntity::class, AnimeStatusEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AniVaultDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun animeDao(): AnimeDao
}