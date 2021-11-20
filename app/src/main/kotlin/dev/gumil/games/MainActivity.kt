package dev.gumil.games

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.gumil.games.ui.GamesListScreen
import dev.gumil.games.ui.MainViewModel
import dev.gumil.games.ui.detail.GameDetailScreen
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
                    GamesApp()
                }
            }
        }
    }

    @Composable
    private fun GamesApp() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "list") {
            composable("list") {
                Column {
                    Toolbar()
                    GamesListScreen(pager = viewModel.pagedGames) { id ->
                        navController.navigate("detail/$id")
                    }
                }
            }
            composable(
                route = "detail/{gameId}",
                arguments = listOf(navArgument("gameId") { type = NavType.StringType })
            ) { backStackEntry ->
                val gameId = backStackEntry.arguments?.getString("gameId")
                gameId?.let { id ->
                    GameDetailScreen(game = viewModel.getGame(id))
                } ?: navController.navigateUp()
            }
        }
    }

    @Composable
    private fun Toolbar() {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = Color.White
                )
            },
            backgroundColor = MaterialTheme.colors.primary
        )
    }
}
