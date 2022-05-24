package com.aminejbr.doublesymmetrytask.data.model


import com.google.gson.annotations.SerializedName

data class CurrentTrackDto(
    val title: String,
    @SerializedName("artwork_url")
    val artworkUrl: String
)