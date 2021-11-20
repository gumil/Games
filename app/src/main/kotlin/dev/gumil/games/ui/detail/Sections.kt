package dev.gumil.games.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import dev.gumil.games.ui.ImageUrl
import dev.gumil.games.ui.SectionUi
import dev.gumil.games.ui.openPage
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
        title = title
    ) {
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
        ) {
            item {
                images.forEach { image ->
                    ImagePreview(image)
                }
            }
        }
    }
}

@Composable
private fun ImagePreview(image: ImageUrl) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.clickable(enabled = image.videoUrl != null) {
            image.videoUrl?.let { context.openPage(it) }
        }
    ) {
        Image(
            painter = rememberImagePainter(
                data = image.url,
            ),
            contentDescription = image.name,
            modifier = Modifier
                .size(height = 320.dp, width = 569.dp)
                .padding(8.dp)
        )
        image.name?.let { name ->
            Text(
                text = name.uppercase(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(MaterialTheme.colors.primaryVariant)
                    .padding(8.dp)
            )
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

@Preview
@Composable
@Suppress("UnusedPrivateMember")
private fun SectionImagePreview() {
    GamesTheme {
        ImagePreview(ImageUrl(url = "url", name = "Trailer"))
    }
}
