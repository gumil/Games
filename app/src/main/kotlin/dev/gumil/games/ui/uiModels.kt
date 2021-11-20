package dev.gumil.games.ui

import dev.gumil.games.data.Game

data class GameUiModel(
    val name: String,
    val cover: ImageUrl,
    val firstReleaseDate: String,
    val totalRating: Int,
    /**
     * Genres, Platforms, Companies, Summary
     */
    val topSections: List<SectionUi>,
    val screenshots: List<ImageUrl>,
    val url: String,
    /**
     * Storyline, Themes, GameMode, PlayerPerspective, GameEngine
     */
    val bottomSections: List<SectionUi>? = null,
    val videos: List<VideoUrl>? = null
)

data class GameListUiModel(
    val id: String,
    val name: String,
    val listPreview: ImageUrl,
    val totalRating: Int,
    val platforms: CommaSeparatedStrings
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

data class CommaSeparatedStrings(
    val strings: List<String>
) {
    override fun toString(): String {
        return strings.joinToString()
    }
}

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
        platforms = CommaSeparatedStrings(platforms.map { it.name })
    )
}

fun GameImageSize.getImageUrl(
    hash: String
) = "https://images.igdb.com/igdb/image/upload/t_$size/$hash.jpg"
