package com.misenpai.anivault.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String?,
    val roleId: Int?,
    val phone: String?,
    val token: String
)