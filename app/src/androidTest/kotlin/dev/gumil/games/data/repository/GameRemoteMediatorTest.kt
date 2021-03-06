package dev.gumil.games.data.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dev.gumil.games.data.Game
import dev.gumil.games.data.db.IgdbDatabase
import dev.gumil.games.data.game
import dev.gumil.games.data.network.IgdbApi
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever

@OptIn(ExperimentalPagingApi::class)
class GameRemoteMediatorTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db: IgdbDatabase = Room
        .inMemoryDatabaseBuilder(context, IgdbDatabase::class.java)
        .build()
    private val api = mock<IgdbApi>()
    private val remoteMediator = GameRemoteMediator(db, api)

    @Test
    fun refresh_load_returns_success_result_when_more_data_is_present() = runBlocking {
        val games = listOf(game(), game())
        whenever(api.getGames(any())).thenReturn(games)

        val pagingState = PagingState<Int, Game>(
            listOf(),
            null,
            PagingConfig(1),
            1
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        assertEquals(games, db.gameDao().getAllGames())
    }

    @Test
    fun refresh_load_success_and_end_of_pagination_when_no_more_data() = runBlocking {
        whenever(api.getGames(any())).thenReturn(emptyList())

        val pagingState = PagingState<Int, Game>(
            listOf(),
            null,
            PagingConfig(1),
            1
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        assertEquals(emptyList<Game>(), db.gameDao().getAllGames())
    }

    @Test
    fun refresh_load_returns_error_result_when_error_occcurs() = runBlocking {
        whenever(api.getGames(any())).then { throw IOException() }

        val pagingState = PagingState<Int, Game>(
            listOf(),
            null,
            PagingConfig(1),
            1
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }

    @Test
    fun prepend_always_succeeds() = runBlocking {
        val pagingState = PagingState<Int, Game>(
            listOf(),
            null,
            PagingConfig(1),
            1
        )
        val result = remoteMediator.load(LoadType.PREPEND, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun append_when_last_item_is_not_null_appends_list() = runBlocking {
        val previousGames = listOf(game(), game())

        db.gameDao().insert(previousGames)

        val games = listOf(game(), game())
        val expected = previousGames + games

        whenever(api.getGames(any())).thenReturn(games)

        val pagingState = PagingState<Int, Game>(
            listOf(PagingSource.LoadResult.Page(
                data = previousGames,
                prevKey = null,
                nextKey = null
            )),
            null,
            PagingConfig(1),
            1
        )
        val result = remoteMediator.load(LoadType.APPEND, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        assertEquals(expected, db.gameDao().getAllGames())
    }

    @After
    fun tearDown() {
        db.clearAllTables()
        reset(api)
    }
}
