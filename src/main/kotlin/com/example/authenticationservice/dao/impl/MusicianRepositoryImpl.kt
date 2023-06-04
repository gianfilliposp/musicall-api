package com.example.authenticationservice.dao.impl

import com.example.authenticationservice.dao.MusicianRepositoryCustom
import com.example.authenticationservice.dto.MusicianEventJobDto
import com.example.authenticationservice.model.Event
import com.example.authenticationservice.model.EventJob
import com.example.authenticationservice.model.Musician
import com.example.authenticationservice.model.MusicianInstrument
import org.springframework.stereotype.Repository
import javax.persistence.criteria.Predicate
import javax.persistence.EntityManager

@Repository
class MusicianRepositoryImpl (
    private val em: EntityManager
): MusicianRepositoryCustom {
    override fun findMusicianByIdAndEventLocation(instrumentId: Long): List<MusicianEventJobDto> {
        val cb = em.criteriaBuilder
        val cq = cb.createQuery(MusicianEventJobDto::class.java)
        val root = cq.from(Musician::class.java)
        cq.select(
            cb.construct(
                MusicianEventJobDto::class.java,
                root.get<Long>("id"),
                root.get<String>("user").get<String>("name"),
                root.get<String>("cep"),
                root.get<String>("imageUrl")
            )
        )

        val joinExec = root.join<Musician, MusicianInstrument>("musicianInstruments")

        val predicates = mutableListOf<Predicate>()
        predicates.add(cb.equal(joinExec.get<Long>("instrumentId"), instrumentId))

        cq.where(*predicates.toTypedArray())

        return em.createQuery(cq).resultList
    }
}