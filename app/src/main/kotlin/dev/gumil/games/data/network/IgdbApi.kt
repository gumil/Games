package dev.gumil.games.data.network

import dev.gumil.games.BuildConfig
import dev.gumil.games.data.Game
import okhttp3.RequestBody
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
}
