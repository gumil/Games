package dev.gumil.games.ui

import dev.gumil.games.data.Game
import java.util.Date

data class GameUiModel(
    val name: String,
    val cover: ImageUrl,
    val firstReleaseDate: Date,
    val platforms: List<ImageUrl>,
    val screenshots: List<ImageUrl>,
    val summary: String,
    val totalRating: Double,
    val url: String,
    val storyline: String? = null,
    val sections: List<SectionUi>? = null,
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
    val thumbnailUrl: String
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
