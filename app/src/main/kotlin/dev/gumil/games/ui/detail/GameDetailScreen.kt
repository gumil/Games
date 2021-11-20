package dev.gumil.games.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import dev.gumil.games.R
import dev.gumil.games.ui.GameUiModel
import dev.gumil.games.ui.ImageUrl
import dev.gumil.games.ui.SectionUi
import dev.gumil.games.ui.common.RatingCircle
import dev.gumil.games.ui.theme.GamesTheme
import java.text.DateFormat
import java.util.Date

@Composable
fun GameDetailScreen(game: GameUiModel) {
    LazyColumn {
        item {
            ContentHeader(game)
            ContentDetails(game, Modifier.padding(16.dp))
        }
    }
}

@Composable
private fun ContentHeader(game: GameUiModel) {
    Box {
        Image(
            painter = rememberImagePainter(
                data = game.cover.url,
                builder = {
                    placeholder(R.drawable.ic_launcher_background)
                }
            ),
            contentDescription = game.cover.name,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(480.dp)
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
                    cover = ImageUrl("cover", "url"),
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
