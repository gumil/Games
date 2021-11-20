package dev.gumil.games.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import dev.gumil.games.ui.ImageUrl
import dev.gumil.games.ui.SectionUi
import dev.gumil.games.ui.theme.GamesTheme

@Composable
fun SectionText(
    sectionUi: SectionUi,
    modifier: Modifier = Modifier
) {
    SectionDetail(
        title = sectionUi.title,
        modifier = modifier
    ) {
        Text(
            text = sectionUi.detail,
            modifier = Modifier.paddingFromBaseline(24.dp)
        )
    }
}

@Composable
fun SectionImage(
    title: String,
    images: List<ImageUrl>,
    modifier: Modifier = Modifier
) {
    SectionDetail(
        title = title,
        modifier = modifier.height(200.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            item {
                images.forEach { image ->
                    Image(
                        painter = rememberImagePainter(data = image.url),
                        contentDescription = image.name
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionDetail(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Divider()
        SectionTitle(title)
        content()
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title.uppercase(),
        fontSize = 12.sp,
        style = MaterialTheme.typography.caption,
        modifier = Modifier.paddingFromBaseline(24.dp)
    )
}

@Preview
@Composable
@Suppress("UnusedPrivateMember")
private fun SectionTextPreview() {
    GamesTheme {
        Surface(color = MaterialTheme.colors.background) {
            SectionText(
                SectionUi(
                    "Genres",
                    "Adventure, Platform"
                )
            )
        }
    }
}
