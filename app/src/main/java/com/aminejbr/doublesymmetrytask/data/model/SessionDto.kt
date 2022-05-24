package com.aminejbr.doublesymmetrytask.data.model


import com.google.gson.annotations.SerializedName

data class SessionDto(
    val name: String,
    @SerializedName("listener_count")
    val listenerCount: Int,
    val genres: List<String>,
    @SerializedName("current_track")
    val currentTrack: CurrentTrackDto
)