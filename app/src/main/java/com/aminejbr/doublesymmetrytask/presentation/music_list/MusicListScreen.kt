package com.aminejbr.doublesymmetrytask.presentation.music_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aminejbr.doublesymmetrytask.R
import com.aminejbr.doublesymmetrytask.common.LoadingSpinner
import com.aminejbr.doublesymmetrytask.presentation.music_list.composables.MusicAppBar
import com.aminejbr.doublesymmetrytask.presentation.music_list.composables.MusicItem
import kotlin.math.min

@Composable
fun MusicListScreen() {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        val viewModel: MusicsViewModel = hiltViewModel()

        // calculate the scrollOffset to epand/collabse the AppBar
        val scrollState = rememberLazyListState()
        val scrollOffset: Float = min(
            1f,
            1 - (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex)
        )
        Column {
            MusicAppBar(scrollOffset, viewModel)
            MusicList(scrollState, viewModel)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicList(
    scrollState: LazyListState,
    viewModel: MusicsViewModel
) {

    val state = viewModel.uiState.value
    val page = viewModel.page.value

    Column {
        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            cells = GridCells.Fixed(2),
            state = scrollState,
            contentPadding = PaddingValues(8.dp)) {
            itemsIndexed(state.musicList) { index, item ->
                // once reaching the end of the list, load next page
                if((index + 1) >= (page * PAGE_SIZE) && (!state.isLoadingPagination)){
                    viewModel.nextPage()
                }
                MusicItem(music = item, modifier = Modifier.padding(8.dp))
            }
        }

        if(state.isLoadingPagination) {
            LoadingSpinner(Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally))
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(state.isLoading) {
            LoadingSpinner()
        }
        if (state.isMusicListEmpty)
            ErrorMessage(error = stringResource(R.string.no_music_message))
        if(state.error.isNotBlank()) {
            ErrorMessage(error = state.error)
        }
    }

}

@Composable
fun ErrorMessage(
    error: String
) {
    Text(error, color = Color.Red, fontSize = 18.sp)
}