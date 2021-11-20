package dev.gumil.games.ui

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import dev.gumil.games.data.Game
import dev.gumil.games.data.repository.GamesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private val repository = mock<GamesRepository>()
    private val viewModel = MainViewModel(repository)
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `games flow emits PagingData of games`() = runBlockingTest(testDispatcher) {
        val games = (0..5).map { Game() }
        val expected = games.map { it.mapToListModel() }
        val flow = flowOf(PagingData.from(games))
        whenever(repository.getPagedPopularGamesFlow()).thenReturn(flow)

        val diffCallback = object : DiffUtil.ItemCallback<GameListUiModel>() {
            override fun areItemsTheSame(oldItem: GameListUiModel, newItem: GameListUiModel): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: GameListUiModel, newItem: GameListUiModel): Boolean {
                return false
            }
        }

        val noopListUpdateCallback = object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {
                // do nothing
            }
            override fun onRemoved(position: Int, count: Int) {
                // do nothing
            }
            override fun onMoved(fromPosition: Int, toPosition: Int) {
                // do nothing
            }
            override fun onChanged(position: Int, count: Int, payload: Any?) {
                // do nothing
            }
        }

        val differ = AsyncPagingDataDiffer(
            diffCallback = diffCallback,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = testDispatcher,
            workerDispatcher = testDispatcher,
        )

        val job = launch {
            viewModel.pagedGames.collectLatest { pagingData ->
                differ.submitData(pagingData)
            }
        }

        advanceUntilIdle()

        assertEquals(expected, differ.snapshot().toList())

        job.cancel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
