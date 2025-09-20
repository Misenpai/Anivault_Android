package com.misenpai.anivault.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.misenpai.anivault.domain.model.User

data class LoginRequest(
    val email: String,
    val password: String
)

data class SignupRequest(
    val name: String,
    val email: String,
    val password: String
)

data class AuthResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("result")
    val result: AuthResult?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("message")
    val message: String?
)

data class AuthResult(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("payload")
    val payload: UserPayload?
)

data class UserPayload(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("role_id")
    val roleId: Int?,
    @SerializedName("phone")
    val phone: String?
) {
    fun toUser(token: String): User = User(
        id = id,
        name = name,
        email = email,
        avatar = avatar,
        roleId = roleId,
        phone = phone,
        token = token
    )
}