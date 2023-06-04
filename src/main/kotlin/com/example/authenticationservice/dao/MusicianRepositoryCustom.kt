package com.example.authenticationservice.dao

import com.example.authenticationservice.dto.MusicianEventJobDto

interface MusicianRepositoryCustom {
    fun findMusicianByIdAndEventLocation(instrumentId: Long): List<MusicianEventJobDto>
}
