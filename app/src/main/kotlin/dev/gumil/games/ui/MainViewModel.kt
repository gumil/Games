package dev.gumil.games.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gumil.games.data.repository.GamesRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GamesRepository
) : ViewModel() {

    val games by lazy {
        repository.getPagedPopularGamesFlow().cachedIn(viewModelScope)
    }
}
