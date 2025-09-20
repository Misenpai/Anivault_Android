package com.misenpai.anivault.data.remote.interceptors

import com.misenpai.anivault.data.local.preferences.UserPreferences
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = runBlocking {
            val token = userPreferences.getToken()
            if (token != null && request.header("Authorization") == null) {
                request.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            } else {
                request
            }
        }
        return chain.proceed(newRequest)
    }
}