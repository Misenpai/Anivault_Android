package com.misenpai.anivault.domain.usecase.anime


import com.misenpai.anivault.core.util.Resource
import com.misenpai.anivault.domain.model.Anime
import com.misenpai.anivault.domain.repository.AnimeRepository
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(query: String, page: Int = 1): Resource<List<Anime>> {
        if (query.isBlank()) {
            return Resource.Error("Search query cannot be empty")
        }
        return repository.searchAnime(query, page)
    }
}