package com.misenpai.anivault.core.constants

import android.content.Context
import com.misenpai.anivault.R

object Constants {
    const val DATABASE_NAME = "anivault_database"
    const val NETWORK_TIMEOUT = 30L
    const val CACHE_TIMEOUT = 5L // minutes

    const val PAGE_SIZE = 25
    const val MAX_TITLE_LENGTH = 50

    object AnimeSeason {
        const val WINTER = "winter"
        const val SPRING = "spring"
        const val SUMMER = "summer"
        const val FALL = "fall"
    }

    object AnimeStatus {
        const val AIRING = "Currently Airing"
        const val FINISHED = "Finished Airing"
        const val UPCOMING = "Not yet aired"
    }

    fun getJikanBaseUrl(context: Context): String =
        context.getString(R.string.JIKAN_API)

    fun getAniVaultBaseUrl(context: Context): String =
        context.getString(R.string.AWS_API)
}