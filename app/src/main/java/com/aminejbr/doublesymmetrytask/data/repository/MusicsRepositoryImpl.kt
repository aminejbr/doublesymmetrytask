package com.aminejbr.doublesymmetrytask.data.repository

import com.aminejbr.doublesymmetrytask.data.MusicApi
import com.aminejbr.doublesymmetrytask.data.model.SessionDto
import com.aminejbr.doublesymmetrytask.domain.repository.MusicsRepository
import javax.inject.Inject

class MusicsRepositoryImpl @Inject constructor(
    private val api: MusicApi
): MusicsRepository {
    override suspend fun getMusics(): List<SessionDto> {
        return api.getMusics().data.sessions
    }

    override suspend fun searchMusic(query: String): List<SessionDto> {
        return api.searchMusic().data.sessions
    }
}