package com.misenpai.anivault.domain.usecase.anime


import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.domain.model.Anime
import com.misenpai.anivault.domain.repository.AnimeRepository
import javax.inject.Inject

class GetCurrentSeasonAnimeUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(page: Int = 1): Resource<List<Anime>> {
        return repository.getCurrentSeasonAnime(page)
    }
}