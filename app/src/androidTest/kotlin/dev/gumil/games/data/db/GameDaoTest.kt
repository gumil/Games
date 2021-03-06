package dev.gumil.games.data.db

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dev.gumil.games.data.Game
import dev.gumil.games.data.game
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class GameDaoTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db: IgdbDatabase = Room
        .inMemoryDatabaseBuilder(context, IgdbDatabase::class.java)
        .build()
    private val gameDao = db.gameDao()

    @Test
    fun insert_a_game_and_get_all_games() = runBlocking {
        val game1 = game(32)
        val game2 = game(1)
        val game3 = game(64)
        val expected = listOf(game1, game2, game3)

        gameDao.insert(listOf(game1, game2, game3))
        val actual = gameDao.getAllGames()

        assertEquals(expected, actual)
    }

    @Test
    fun inserting_an_empty_list_does_nothing() = runBlocking {
        val game = game()
        val expected = listOf(game)

        gameDao.insert(listOf(game))
        gameDao.insert(emptyList())
        val actual = gameDao.getAllGames()

        assertEquals(expected, actual)
    }

    @Test
    fun insert_a_game_with_nullable_fields() = runBlocking {
        val game = game().copy(
            storyline = null,
            themes = null,
            gameModes = null,
            gameEngines = null,
            involvedCompanies = null,
            playerPerspectives = null,
            videos = null
        )
        val expected = listOf(game)

        gameDao.insert(listOf(game))
        val actual = gameDao.getAllGames()

        assertEquals(expected, actual)
    }

    @Test
    fun insert_game_list_and_replace_duplicate_games() = runBlocking {
        val game = game()
        val expected = listOf(game)

        gameDao.insert(listOf(game))
        gameDao.insert(listOf(game))
        gameDao.insert(listOf(game))
        val actual = gameDao.getAllGames()

        assertEquals(expected, actual)
    }

    @Test
    fun insert_games_and_clear_all() = runBlocking {
        val expected = emptyList<Game>()

        gameDao.insert((0..5).map { game() })
        gameDao.clearAll()
        val actual = gameDao.getAllGames()

        assertEquals(expected, actual)
    }

    @Test
    fun insert_games_and_refresh_pager() = runBlocking {
        val game1 = game(32)
        val game2 = game(1)
        val game3 = game(64)

        val expected = PagingSource.LoadResult.Page(
            data = listOf(game1),
            nextKey = 1,
            prevKey = null,
            itemsBefore = 0,
            itemsAfter = 2
        )

        gameDao.insert(listOf(game1, game2, game3))
        val actual = gameDao.getPagedGames().load(
            PagingSource.LoadParams.Refresh(
                null,
                1,
                false
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun insert_games_and_append_pager() = runBlocking {
        val game = game()

        val expected = PagingSource.LoadResult.Page(
            data = listOf(game),
            nextKey = 2,
            prevKey = 1
        )

        gameDao.insert(listOf(game(), game, game(), game()))
        val actual = gameDao.getPagedGames().load(
            PagingSource.LoadParams.Append(
                1,
                1,
                false
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun insert_a_game_and_get_game_by_id() = runBlocking {
        val id = Random.nextInt()
        val game = game(id)

        gameDao.insert(listOf(game(), game, game(), game()))
        gameDao.getGame(id.toString())
            .take(1)
            .collect { actual ->
                assertEquals(game, actual)
            }
    }

    @After
    fun closeDb() {
        db.close()
    }
}