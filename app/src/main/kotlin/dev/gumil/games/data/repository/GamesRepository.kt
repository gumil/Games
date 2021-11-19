package dev.gumil.games.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.gumil.games.data.Game
import dev.gumil.games.data.db.IgdbDatabase
import dev.gumil.games.data.network.IgdbApi
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getPagedPopularGamesFlow(): Flow<PagingData<Game>>
    suspend fun getGame(id: String): Game
}

@OptIn(ExperimentalPagingApi::class)
class GamesRepositoryImpl(
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

    override suspend fun getGame(id: String): Game {
        return db.gameDao().getGame(id)
    }
}