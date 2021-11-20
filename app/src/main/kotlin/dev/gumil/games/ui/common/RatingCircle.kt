package dev.gumil.games.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gumil.games.ui.theme.GamesTheme

@Composable
@Suppress("MagicNumber")
fun RatingCircle(
    rating: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.White)
            .padding(4.dp)
    ) {
        CircularProgressIndicator(
            progress = rating / 100f
        )
        Text(
            text = rating.toString(),
            color = Color.Black,
            fontWeight = FontWeight.Black,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun RatingCirclePreview() {
    GamesTheme {
        RatingCircle(rating = 88)
    }
}
