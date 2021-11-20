package dev.gumil.games

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import dagger.hilt.android.AndroidEntryPoint
import dev.gumil.games.ui.GamesList
import dev.gumil.games.ui.MainViewModel
import dev.gumil.games.ui.theme.GamesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    GamesList(pager = viewModel.pagedGames)
                }
            }
        }
    }
}
