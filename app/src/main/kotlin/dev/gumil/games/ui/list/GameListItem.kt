package dev.gumil.games.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import dev.gumil.games.ui.GameListUiModel
import dev.gumil.games.ui.ImageUrl
import dev.gumil.games.ui.common.RatingCircle
import dev.gumil.games.ui.theme.GamesTheme

@Composable
@Suppress("MagicNumber")
fun GameListItem(
    game: GameListUiModel,
    modifier: Modifier = Modifier,
    navigateToDetailScreen: (gameId: String) -> Unit
) {
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color.Transparent, Color.Black),
        startY = sizeImage.height.toFloat() / 3,
        endY = sizeImage.height.toFloat()
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                navigateToDetailScreen(game.id)
            }
    ) {
        Image(
            painter = rememberImagePainter(game.listPreview.url),
            contentDescription = game.listPreview.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .onGloballyPositioned {
                    sizeImage = it.size
                }
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(gradient)
        )
        ListItemContent(game, Modifier.matchParentSize())
    }
}

@Composable
private fun ListItemContent(
    game: GameListUiModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Text(
                text = game.name,
                color = Color.White,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = game.platforms,
                color = Color.White,
                style = MaterialTheme.typography.subtitle2
            )
        }

        RatingCircle(
            rating = game.totalRating,
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}

@Preview
@Composable
@Suppress("UnusedPrivateMember")
private fun GameListItemPreview() {
    val model = GameListUiModel(
        id = "1234",
        name = "Super Game",
        listPreview = ImageUrl("url"),
        totalRating = 88,
        platforms = "Playstation 88, Nintendo sWiitch"
    )

    GamesTheme {
        GameListItem(game = model) { }
    }
}
