package com.misenpai.anivault.domain.usecase.anime

import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.domain.model.AnimeDetails
import com.misenpai.anivault.domain.repository.AnimeRepository
import javax.inject.Inject

class GetAnimeDetailsUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): Resource<AnimeDetails> {
        return repository.getAnimeById(animeId)
    }
}