package com.misenpai.anivault.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("result")
    val result: UserResult?
)

data class UserResult(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("payload")
    val payload: UserPayload?
)

data class UpdateProfileRequest(
    val name: String?,
    val phone: String?,
    val avatar: String?
)

data class BaseResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String?
)