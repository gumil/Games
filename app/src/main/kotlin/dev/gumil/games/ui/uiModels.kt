package dev.gumil.games.ui

import dev.gumil.games.data.FieldName
import dev.gumil.games.data.Game
import java.util.Date

data class GameUiModel(
    val id: String,
    val name: String,
    val cover: ImageUrl,
    val firstReleaseDate: Date,
    val totalRating: Int,
    /**
     * Genres, Platforms, Companies, Summary
     */
    val topSections: List<SectionUi?>,
    val screenshots: List<ImageUrl>,
    val url: String,
    /**
     * Storyline, Themes, GameMode, PlayerPerspective, GameEngine
     */
    val bottomSections: List<SectionUi?>,
    val videos: List<ImageUrl>? = null
)

data class GameListUiModel(
    val id: String,
    val name: String,
    val listPreview: ImageUrl,
    val totalRating: Int,
    val platforms: String
)

data class SectionUi(
    val title: String,
    val detail: String
)

data class ImageUrl(
    val url: String,
    val name: String? = null,
    val videoUrl: String? = null
)

enum class GameImageSize(
    val size: String
) {
    LIST("screenshot_big"),
    COVER("cover_big_2x"),
    SCREENSHOTS("screenshot_big")
}

fun Game.mapToListModel(): GameListUiModel {
    return GameListUiModel(
        id = id,
        name = name,
        listPreview = ImageUrl(url = GameImageSize.LIST.getImageUrl(cover.imageId)),
        totalRating = totalRating.toInt(),
        platforms = platforms.joinToString { it.name }
    )
}

@Suppress("MagicNumber")
fun Game.mapToDetailModel(): GameUiModel {
    return GameUiModel(
        id = id,
        name = name,
        firstReleaseDate = Date(firstReleaseDate * 1000), // Converted to milliseconds
        cover = ImageUrl(url = GameImageSize.COVER.getImageUrl(cover.imageId)),
        totalRating = totalRating.toInt(),
        url = url,
        topSections = listOf(
            genres?.toSectionUi("Genres"),
            platforms
                .map { FieldName(it.name) }
                .toSectionUi("Platforms"),
            involvedCompanies
                ?.map { it.company }
                ?.toSectionUi("Companies"),
            SectionUi("Summary", summary)
        ),
        screenshots = screenshots.map { image ->
            ImageUrl(url = GameImageSize.SCREENSHOTS.getImageUrl(image.imageId))
        },
        bottomSections = listOf(
            storyline?.let { SectionUi("Storyline", it) },
            themes?.toSectionUi("Themes"),
            gameModes?.toSectionUi("Game Modes"),
            playerPerspectives?.toSectionUi("Player Perspective"),
            gameEngines?.toSectionUi("Game Engines")
        ),
        videos = videos?.map { video ->
            ImageUrl(
                url = "https://i.ytimg.com/vi/${video.videoId}/hq720.jpg",
                name = video.name,
                videoUrl = "https://www.youtube.com/watch?v=${video.videoId}"
            )
        }
    )
}

fun GameImageSize.getImageUrl(
    hash: String
) = "https://images.igdb.com/igdb/image/upload/t_$size/$hash.jpg"

fun List<FieldName>.toSectionUi(title: String) = SectionUi(title, joinToString { it.name })
