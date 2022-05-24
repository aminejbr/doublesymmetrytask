package com.aminejbr.doublesymmetrytask.presentation.music_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aminejbr.doublesymmetrytask.common.Resource
import com.aminejbr.doublesymmetrytask.domain.model.Music
import com.aminejbr.doublesymmetrytask.domain.usecase.GetMusicsUseCase
import com.aminejbr.doublesymmetrytask.domain.usecase.SearchMusicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PAGE_SIZE = 10
const val MAX_PAGES = 5

@HiltViewModel
class MusicsViewModel @Inject constructor(
    private val getMusicsUseCase: GetMusicsUseCase,
    private val searchMusicUseCase: SearchMusicUseCase,
): ViewModel() {

    val uiState = mutableStateOf(MusicListState())

    private val searchQuery = mutableStateOf("")

    private var searchJob: Job? = null

    val page = mutableStateOf(1)

    init {
        loadMusics()
    }

    private fun loadMusics() {
        viewModelScope.launch {
            getMusicsUseCase().collect { result ->
                when(result) {
                    is Resource.Success -> {
                        result.data?.let {
                            if (it.isEmpty()) {
                                showEmptyList()
                            } else {
                                uiState.value = uiState.value.copy(
                                    musicList = result.data,
                                    isLoading = false
                                )
                            }
                        } ?: showError(result.message ?: "Something wrong happened. Please try again later.")
                    }
                    is Resource.Error -> {
                        showError(result.message ?: "Something wrong happened. Please try again later.")
                    }
                    is Resource.Loading -> {
                        showLoading()
                    }
                }
            }
        }
    }

    fun nextPage(){
        viewModelScope.launch {
            if(page.value < MAX_PAGES){
                uiState.value = uiState.value.copy(
                    isLoadingPagination = true
                )
                incrementPage()

                // pretend waiting for a response
                delay(1500)

                // appending the same mock response. (first 10 musics)
                val firstMockResponse = uiState.value.musicList.take(10)
                appendMusics(firstMockResponse)
            }
        }
    }

    fun searchMusic(query: String) {
        searchQuery.value = query

        // Use the appropriate debounce when receiving text
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            resetSearchState()

            searchMusicUseCase(query).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        result.data?.let {
                            if (it.isEmpty()) {
                                showEmptyList()
                            } else {
                                // fetch the mock response and randomly shuffle the array of items to display
                                uiState.value = uiState.value.copy(
                                    musicList = result.data.shuffled(),
                                    isLoadingSearch = false
                                )
                            }
                        } ?: showError(result.message ?: "Something wrong happened. Please try again later.")
                    }
                    is Resource.Error -> {
                        showError(result.message ?: "Something wrong happened. Please try again later.")
                    }
                    is Resource.Loading -> {
                        showSearchLoading()
                    }
                }
            }
        }
    }

    private fun appendMusics(musics: List<Music>){
        val currentMusicList = ArrayList(uiState.value.musicList)
        currentMusicList.addAll(musics)
        uiState.value = uiState.value.copy(
            musicList = currentMusicList,
            isLoadingPagination = false,
            isLoadingSearch = false
        )
    }

    private fun incrementPage(){
        page.value = page.value + 1
    }

    private fun resetSearchState() {
        uiState.value = uiState.value.copy(musicList = emptyList())
        page.value = 1
    }

    private fun showEmptyList() {
        uiState.value = uiState.value.copy(
            isLoading = false,
            isLoadingSearch = false,
            isMusicListEmpty = true
        )
    }

    private fun showLoading() {
        uiState.value = uiState.value.copy(
            isLoading = true
        )
    }

    private fun showSearchLoading() {
        uiState.value = uiState.value.copy(
            isLoadingSearch = true
        )
    }

    private fun showError(errorMessage: String) {
        uiState.value = uiState.value.copy(
            error = errorMessage,
            isLoading = false,
            isLoadingPagination = false,
            isLoadingSearch = false
        )
    }
}