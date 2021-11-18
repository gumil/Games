package dev.gumil.games.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Game(
    @Json(name = "id")
    val id: Int,
    @Json(name = "cover")
    val cover: GameImage,
    @Json(name = "first_release_date")
    val firstReleaseDate: Int,
    @Json(name = "game_modes")
    val gameModes: List<FieldName>,
    @Json(name = "game_engines")
    val gameEngines: List<FieldName>,
    @Json(name = "genres")
    val genres: List<FieldName>,
    @Json(name = "involved_companies")
    val involvedCompanies: List<InvolvedCompany>,
    @Json(name = "name")
    val name: String,
    @Json(name = "platforms")
    val platforms: List<GamePlatform>,
    @Json(name = "player_perspectives")
    val playerPerspectives: List<FieldName>,
    @Json(name = "screenshots")
    val screenshots: List<GameImage>,
    @Json(name = "storyline")
    val storyline: String,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "themes")
    val themes: List<FieldName>,
    @Json(name = "total_rating")
    val totalRating: Double,
    @Json(name = "url")
    val url: String,
    @Json(name = "videos")
    val videos: List<GameVideo>
)

@JsonClass(generateAdapter = true)
data class InvolvedCompany(
    @Json(name = "company")
    val company: FieldName
)

@JsonClass(generateAdapter = true)
data class GamePlatform(
    @Json(name = "name")
    val name: String,
    @Json(name = "platform_logo")
    val platformLogo: GameImage
)

@JsonClass(generateAdapter = true)
data class FieldName(
    @Json(name = "name")
    val name: String
)

@JsonClass(generateAdapter = true)
data class GameImage(
    @Json(name = "image_id")
    val imageId: String
)

@JsonClass(generateAdapter = true)
data class GameVideo(
    @Json(name = "name")
    val name: String,
    @Json(name = "video_id")
    val videoId: String
)
