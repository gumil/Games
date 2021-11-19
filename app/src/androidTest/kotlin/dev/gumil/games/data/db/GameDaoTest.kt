package dev.gumil.games.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dev.gumil.games.data.Game
import dev.gumil.games.data.game
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class GameDaoTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db: IgdbDatabase = Room
        .inMemoryDatabaseBuilder(context, IgdbDatabase::class.java)
        .build()
    private val gameDao = db.gameDao()

    @Test
    fun insert_a_game_and_get_all_games() = runBlocking {
        val game = game()
        val expected = listOf(game)

        gameDao.insert(listOf(game))
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

    @After
    fun closeDb() {
        db.close()
    }
}