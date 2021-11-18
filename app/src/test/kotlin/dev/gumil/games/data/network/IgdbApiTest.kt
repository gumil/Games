package dev.gumil.games.data.network

import dev.gumil.games.data.FieldName
import dev.gumil.games.data.Game
import dev.gumil.games.data.GameImage
import dev.gumil.games.data.GamePlatform
import dev.gumil.games.data.GameVideo
import dev.gumil.games.data.InvolvedCompany
import dev.gumil.games.mockResponseFrom
import kotlinx.coroutines.runBlocking
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class IgdbApiTest {
    private val mockServer = MockWebServer()
    private val api = ApiFactory.createApi(mockServer.url("/").toString())

    @Test
    fun `getGames returns a list of games`() = runBlocking {
        mockServer.enqueue(mockResponseFrom("getPopularGames.json"))

        val game = Game(
            id = 134161,
            cover = GameImage(
                imageId = "co48pg"
            ),
            firstReleaseDate = 1637020800,
            gameEngines = listOf(
                FieldName("Unreal Engine")
            ),
            gameModes = listOf(
                FieldName("Single player")
            ),
            genres = listOf(
                FieldName("Puzzle"),
                FieldName("Adventure")
            ),
            involvedCompanies = listOf(
                InvolvedCompany(
                    company = FieldName("lorem")
                )
            ),
            name = "The lorem game",
            platforms = listOf(
                GamePlatform(
                    name = "PC (Microsoft Windows)",
                    platformLogo = GameImage(
                        imageId = "irwvwpl023f8y19tidgq"
                    )
                )
            ),
            playerPerspectives = listOf(
                FieldName("Third person")
            ),
            screenshots = listOf(
                GameImage(
                    imageId = "scbucr"
                )
            ),
            storyline = "Lorem ipsum dolor sit amet.",
            summary = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            themes = listOf(
                FieldName("Open world")
            ),
            totalRating = 88.8,
            url = "https://www.igdb.com/games/lorem",
            videos = listOf(
                GameVideo(
                    name = "Trailer",
                    videoId = "UKuSbg2o7Q8"
                )
            )
        )

        val expected = listOf(game)

        val actual = api.getGames("${Random.nextInt()}".toRequestBody())

        assertEquals(expected, actual)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}
