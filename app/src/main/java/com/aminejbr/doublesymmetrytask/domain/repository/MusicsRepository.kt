package com.aminejbr.doublesymmetrytask.domain.repository

import com.aminejbr.doublesymmetrytask.data.model.SessionDto

interface MusicsRepository {
    suspend fun getMusics(): List<SessionDto>
    suspend fun searchMusic(query: String): List<SessionDto>
}