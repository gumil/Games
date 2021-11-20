package dev.gumil.games.data.network

import dev.gumil.games.BuildConfig
import dev.gumil.games.data.Game
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IgdbApi {
    @Headers(
        "Client-ID: ${BuildConfig.CLIENT_ID}",
        "Authorization: Bearer ${BuildConfig.TOKEN}"
    )
    @POST("v4/games")
    suspend fun getGames(@Body query: RequestBody): List<Game>

    companion object {
        fun queryPopularGames(offset: Int): RequestBody {
            return """
                fields
                    name,
                    total_rating,
                    cover.image_id,
                    platforms.name,
                    platforms.platform_logo.image_id,
                    first_release_date,
                    game_modes.name,
                    game_engines.name,
                    genres.name,
                    involved_companies.company.name,
                    player_perspectives.name,
                    storyline,
                    summary,
                    screenshots.image_id,
                    themes.name,
                    url,
                    videos.name,
                    videos.video_id;
                where
                    first_release_date != null &
                    first_release_date < ${System.currentTimeMillis()} &
                    total_rating > 70 &
                    total_rating_count > 5 &
                    parent_game = null;
                sort first_release_date desc;
                limit 10;
                offset $offset;
            """.trimIndent()
                .toRequestBody()
        }
    }
}
