package dev.gumil.games.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import dev.gumil.games.ui.GameUiModel
import dev.gumil.games.ui.ImageUrl
import dev.gumil.games.ui.SectionUi
import dev.gumil.games.ui.common.RatingCircle
import dev.gumil.games.ui.theme.GamesTheme
import kotlinx.coroutines.flow.Flow
import java.text.DateFormat
import java.util.Date
import kotlin.math.min

@Composable
fun GameDetailScreen(game: Flow<GameUiModel>) {
    val state = game.collectAsState(initial = null)

    state.value?.let { gameModel ->
        GameDetailScreen(game = gameModel)
    }
}

@Composable
@Suppress("MagicNumber")
private fun GameDetailScreen(game: GameUiModel) {
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        val height = 480.dp
        ContentHeader(
            game = game,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .graphicsLayer {
                    alpha = min(1f, 1 - (scrollState.value / (height.value * 2)))
                    translationY = -scrollState.value * 0.1f
                }
        )
        ContentDetails(game, Modifier.padding(16.dp))
    }
}

@Composable
private fun ContentHeader(
    game: GameUiModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = rememberImagePainter(
                data = game.cover.url
            ),
            contentDescription = game.cover.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        RatingCircle(
            rating = game.totalRating,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
private fun ContentDetails(
    game: GameUiModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = game.name,
            style = MaterialTheme.typography.h4
        )
        Text(
            text = DateFormat.getDateInstance().format(game.firstReleaseDate),
            style = MaterialTheme.typography.h6
        )

        game.topSections.filterNotNull().forEach { section ->
            SectionText(section, modifier = Modifier.padding(vertical = 8.dp))
        }

        SectionImage(title = "Screenshots", images = game.screenshots)

        game.bottomSections.filterNotNull().forEach { section ->
            SectionText(section, modifier = Modifier.padding(vertical = 8.dp))
        }

        game.videos?.let { videos ->
            SectionImage(title = "Videos", images = videos)
        }
    }
}

@Composable
@Preview
@Suppress("MagicNumber", "UnusedPrivateMember")
private fun GameDetailScreenPreview() {
    GamesTheme {
        Surface(color = MaterialTheme.colors.background) {
            GameDetailScreen(
                GameUiModel(
                    id = "1234",
                    name = "Super Game",
                    cover = ImageUrl("url"),
                    firstReleaseDate = Date(),
                    totalRating = 88,
                    topSections = listOf(
                        SectionUi(
                            title = "Genres",
                            detail = "Adventure, Platform"
                        ),
                        SectionUi(
                            title = "Platforms",
                            detail = "Playstation 88, Xbox Series Z"
                        ),
                        SectionUi(
                            title = "Companies",
                            detail = "Ubihard, CircleEnix"
                        ),
                        SectionUi(
                            title = "Summary",
                            detail = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                        )
                    ),
                    screenshots = listOf(
                        ImageUrl("screenshot", "url")
                    ),
                    url = "url",
                    bottomSections = (0..5).map { index ->
                        SectionUi(
                            title = "Section $index",
                            detail = "Detail $index, Detail $index, Detail $index"
                        )
                    }
                )
            )
        }
    }
}
