package dev.gumil.games.data.repository

import dev.gumil.games.data.Game
import dev.gumil.games.data.db.GameDao
import dev.gumil.games.data.db.IgdbDatabase
import dev.gumil.games.data.network.IgdbApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.random.Random

class GamesRepositoryImplTest {
    private val api = mock<IgdbApi>()
    private val db = mock<IgdbDatabase>()
    private val repository = GamesRepositoryImpl(db, api)

    @Test
    fun `getGame gets a game from database given an id`() = runBlocking {
        val id = "${Random.nextInt()}"
        val expected = Game()
        val dao = mock<GameDao>()

        whenever(db.gameDao()).thenReturn(dao)
        whenever(db.gameDao().getGame(id)).thenReturn(flowOf(expected))

        repository.getGame(id)
            .collect { actual ->
                assertEquals(expected, actual)
            }
    }
}
