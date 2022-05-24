package com.aminejbr.doublesymmetrytask.domain.model

import com.aminejbr.doublesymmetrytask.data.model.SessionDto

data class Music(
    val listenerCount: String,
    val image: String,
    val title: String,
    val genres: String
)

fun SessionDto.toMusic(): Music {
    return Music(
        title = currentTrack.title,
        image = currentTrack.artworkUrl,
        genres = genres.joinToString(),
        listenerCount = listenerCount.toString(),
    )
}
