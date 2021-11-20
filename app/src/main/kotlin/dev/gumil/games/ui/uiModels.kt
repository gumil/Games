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
    val videos: List<VideoUrl>? = null
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
    val name: String,
    val url: String
)

data class VideoUrl(
    val name: String,
    val url: String,
    val videoUrl: String
)

enum class GameImageSize(
    val size: String
) {
    THUMB("thumb_2x"),
    LIST("screenshot_big"),
    COVER("cover_big_2x"),
    SCREENSHOTS("720p")
}

fun Game.mapToListModel(): GameListUiModel {
    return GameListUiModel(
        id = id,
        name = name,
        listPreview = ImageUrl(name, GameImageSize.LIST.getImageUrl(cover.imageId)),
        totalRating = totalRating.toInt(),
        platforms = platforms.joinToString { it.name }
    )
}

fun Game.mapToDetailModel(): GameUiModel {
    return GameUiModel(
        id = id,
        name = name,
        firstReleaseDate = Date(firstReleaseDate),
        cover = ImageUrl(name, GameImageSize.COVER.getImageUrl(cover.imageId)),
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
            ImageUrl("screenshot", GameImageSize.SCREENSHOTS.getImageUrl(image.imageId))
        },
        bottomSections = listOf(
            storyline?.let { SectionUi("Storyline", it) },
            themes?.toSectionUi("Themes"),
            gameModes?.toSectionUi("Game Modes"),
            playerPerspectives?.toSectionUi("Player Perspective"),
            gameEngines?.toSectionUi("Game Engines")
        )
    )
}

fun GameImageSize.getImageUrl(
    hash: String
) = "https://images.igdb.com/igdb/image/upload/t_$size/$hash.jpg"

fun List<FieldName>.toSectionUi(title: String) = SectionUi(title, joinToString { it.name })
