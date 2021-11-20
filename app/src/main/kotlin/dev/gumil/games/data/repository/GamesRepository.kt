package dev.gumil.games.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.scopes.ViewModelScoped
import dev.gumil.games.data.Game
import dev.gumil.games.data.db.IgdbDatabase
import dev.gumil.games.data.network.IgdbApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GamesRepository {
    fun getPagedPopularGamesFlow(): Flow<PagingData<Game>>
    fun getGame(id: String): Flow<Game>
}

@OptIn(ExperimentalPagingApi::class)
@ViewModelScoped
class GamesRepositoryImpl @Inject constructor(
    private val db: IgdbDatabase,
    private val api: IgdbApi
) : GamesRepository {
    override fun getPagedPopularGamesFlow(): Flow<PagingData<Game>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10
        ),
        remoteMediator = GameRemoteMediator(db, api)
    ) {
        db.gameDao().getPagedGames()
    }.flow

    override fun getGame(id: String): Flow<Game> {
        return db.gameDao().getGame(id)
    }
}
