package com.aminejbr.doublesymmetrytask.data

import com.aminejbr.doublesymmetrytask.common.Constants.MUSICS_END_POINT
import com.aminejbr.doublesymmetrytask.common.Constants.SEARCH_MUSIC_END_POINT
import com.aminejbr.doublesymmetrytask.data.model.MusicsResponse
import retrofit2.http.GET

interface MusicApi {
    @GET(MUSICS_END_POINT)
    suspend fun getMusics(): MusicsResponse

    @GET(SEARCH_MUSIC_END_POINT)
    suspend fun searchMusic(): MusicsResponse
}