package com.example.authenticationservice.dao

import com.example.authenticationservice.dto.MusicianEventJobDto
import com.example.authenticationservice.dto.PageMusicianEventJobDto
import com.example.authenticationservice.model.Musician
import com.example.authenticationservice.parameters.FilterMusicianRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import javax.persistence.Tuple

interface MusicianRepositoryCustom {
    fun findMusicianByIdAndEventLocation(instrumentId: Long, filterMusicianRequest: FilterMusicianRequest, pageable: Pageable): PageImpl<MusicianEventJobDto>

}
