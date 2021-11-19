package dev.gumil.games.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.gumil.games.data.Game
import dev.gumil.games.data.db.IgdbDatabase
import dev.gumil.games.data.network.IgdbApi
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class GameRemoteMediator(
    private val database: IgdbDatabase,
    private val igdbApi: IgdbApi
) : RemoteMediator<Int, Game>() {

    private val dao = database.gameDao()
    private val initialOffset = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Game>
    ): MediatorResult {
        return try {
            val offset = when (loadType) {
                LoadType.REFRESH -> initialOffset
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    state.pages.size
                }
            }

            val response = igdbApi.getGames(
                IgdbApi.queryPopularGames(offset)
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dao.clearAll()
                }

                dao.insert(response)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
