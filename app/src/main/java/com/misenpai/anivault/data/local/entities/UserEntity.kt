package com.misenpai.anivault.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.misenpai.anivault.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String?,
    val roleId: Int?,
    val phone: String?,
    val token: String
){
    fun toDomainModel(): User = User(
        id = id,
        name = name,
        email = email,
        avatar = avatar,
        roleId = roleId,
        phone = phone,
        token = token
    )
}

fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    name = name,
    email = email,
    avatar = avatar,
    roleId = roleId,
    phone = phone,
    token = token
)