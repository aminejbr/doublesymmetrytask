package com.aminejbr.doublesymmetrytask.domain.usecase

import com.aminejbr.doublesymmetrytask.common.Resource
import com.aminejbr.doublesymmetrytask.data.model.CurrentTrackDto
import com.aminejbr.doublesymmetrytask.data.model.SessionDto
import com.aminejbr.doublesymmetrytask.domain.model.Music
import com.aminejbr.doublesymmetrytask.domain.model.toMusic
import com.aminejbr.doublesymmetrytask.domain.repository.MusicsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException

class SearchMusicUseCaseTest {

    private lateinit var musicsRepository: MusicsRepository
    private lateinit var searchMusicUseCase: SearchMusicUseCase

    @Before
    fun before() {
        musicsRepository = mock()
        searchMusicUseCase = SearchMusicUseCase(musicsRepository)
    }

    @Test
    fun `Given MusicsRepository result, When invoking SearchMusicUseCase, emit expected result`() {

        // mock response from MusicsRepository
        val musicsRepositoryResult: List<SessionDto> = listOf(
            SessionDto(
                "Name 1",
                1,
                listOf("genre1", "genre2", "genre3"),
                CurrentTrackDto("title1", "img_url_1")
            ),
            SessionDto(
                "Name 2",
                2,
                listOf("genre1", "genre2", "genre3"),
                CurrentTrackDto("title2", "img_url_2")
            )
        )

        runBlocking {
            whenever(musicsRepository.searchMusic(""))
                .thenReturn(musicsRepositoryResult)

            // instantiate resource with Loading
            var resource: Resource<List<Music>> = Resource.Loading()

            searchMusicUseCase("").collect {
                resource = it
            }

            assertEquals(musicsRepositoryResult.map { it.toMusic() }, resource.data)
        }
    }

    @Test
    fun `Given MusicsRepository throws IOException, When invoking SearchMusicUseCase, emit expected error message`() {

        runBlocking {
            given(musicsRepository.searchMusic("")).willAnswer {
                throw IOException()
            }

            // instantiate resource with Loading
            var resource: Resource<List<Music>> = Resource.Loading()

            searchMusicUseCase("").collect {
                resource = it
            }

            assertEquals("Please check your Internet connection.", resource.message)
        }
    }

    @Test
    fun `Given MusicsRepository throws Exception, When invoking SearchMusicUseCase, emit expected error message`() {

        runBlocking {
            given(musicsRepository.searchMusic("")).willAnswer {
                throw Exception()
            }

            // instantiate resource with Loading
            var resource: Resource<List<Music>> = Resource.Loading()

            searchMusicUseCase("").collect {
                resource = it
            }

            assertEquals("Something wrong happened. Please try again later.", resource.message)
        }
    }
}