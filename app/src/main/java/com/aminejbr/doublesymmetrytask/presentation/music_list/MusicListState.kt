package com.aminejbr.doublesymmetrytask.presentation.music_list

import com.aminejbr.doublesymmetrytask.domain.model.Music

data class MusicListState(
    val musicList: List<Music> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingPagination: Boolean = false,
    val isLoadingSearch: Boolean = false,
    val isMusicListEmpty: Boolean = false,
    val error: String = ""
)
