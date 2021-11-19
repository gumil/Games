package dev.gumil.games.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gumil.games.data.repository.GamesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GamesRepository
) : ViewModel() {

    val games by lazy {
        repository
            .getPagedPopularGamesFlow()
            .map { data ->
                data.map { it.mapToListModel() }
            }
            .cachedIn(viewModelScope)
    }
}
