package dev.gumil.games.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.gumil.games.ui.GameListUiModel
import kotlinx.coroutines.flow.Flow

@Composable
fun GamesListScreen(
    pager: Flow<PagingData<GameListUiModel>>,
    navigateToDetailScreen: (gameId: String) -> Unit
) {
    val lazyPagingItems = pager.collectAsLazyPagingItems()
    val isRefreshing = lazyPagingItems.loadState.refresh == LoadState.Loading
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            lazyPagingItems.refresh()
        }
    ) {
        if (lazyPagingItems.itemCount == 0 && !isRefreshing) {
            Column {
                repeat(4) {
                    Placeholder()
                }
            }
        } else {
            GamesList(lazyPagingItems, navigateToDetailScreen)
        }
    }
}

@Composable
private fun GamesList(
    lazyPagingItems: LazyPagingItems<GameListUiModel>,
    navigateToDetailScreen: (gameId: String) -> Unit
) {
    LazyColumn {
        itemsIndexed(lazyPagingItems) { _, item ->
            item?.let {
                GameListItem(
                    game = item,
                    navigateToDetailScreen = navigateToDetailScreen
                )
            }
        }

        if (lazyPagingItems.loadState.append == LoadState.Loading) {
            item {
                Placeholder()
            }
        }
    }
}
