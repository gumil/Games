package dev.gumil.games.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.gumil.games.data.repository.GamesRepository

class MainViewModel(
    private val repository: GamesRepository
) : ViewModel() {

    val games by lazy {
        repository.getPagedPopularGamesFlow().cachedIn(viewModelScope)
    }
}
