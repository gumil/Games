package dev.gumil.games.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@Composable
@Suppress("MagicNumber")
fun Placeholder() {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Spacer(
                Modifier
                    .width(100.dp)
                    .height(18.dp)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.fade(),
                    )
            )
            Spacer(
                Modifier
                    .padding(top = 8.dp)
                    .width(200.dp)
                    .height(12.dp)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.fade(),
                    )
            )
        }

        Spacer(
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .align(Alignment.TopEnd)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.fade(),
                )
        )
    }
}

@Preview
@Composable
@Suppress("UnusedPrivateMember")
private fun PlaceholderPreview() {
    Placeholder()
}
