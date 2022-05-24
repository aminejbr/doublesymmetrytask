package com.aminejbr.doublesymmetrytask.domain.usecase

import com.aminejbr.doublesymmetrytask.common.Resource
import com.aminejbr.doublesymmetrytask.domain.model.Music
import com.aminejbr.doublesymmetrytask.domain.model.toMusic
import com.aminejbr.doublesymmetrytask.domain.repository.MusicsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetMusicsUseCase @Inject constructor(
    private val musicsRepository: MusicsRepository
) {
    // First emit Loading, then emit Success or Error.
    operator fun invoke(): Flow<Resource<List<Music>>> = flow {
        try {
            emit(Resource.Loading<List<Music>>())
            val musics = musicsRepository.getMusics()
                .map {
                    it.toMusic()
                }
            emit(Resource.Success<List<Music>>(musics))
        } catch (e: IOException) {
            emit(Resource.Error<List<Music>>("Please check your Internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error<List<Music>>("Something wrong happened. Please try again later."))
        }
    }
}