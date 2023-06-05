package com.example.authenticationservice.dao

import com.example.authenticationservice.dto.MusicianEventJobDto
import com.example.authenticationservice.parameters.FilterMusicianRequest
import javax.persistence.Tuple

interface MusicianRepositoryCustom {
    fun findMusicianByIdAndEventLocation(instrumentId: Long, filterMusicianRequest: FilterMusicianRequest): List<MusicianEventJobDto>
}
