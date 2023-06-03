package com.example.authenticationservice.dao.impl

import com.example.authenticationservice.dao.EventRepositoryCustom
import com.example.authenticationservice.dto.EventSearchDto
import com.example.authenticationservice.model.Event
import com.example.authenticationservice.model.EventJob
import com.example.authenticationservice.parameters.FilterEventsRequest
import org.springframework.stereotype.Repository
import java.time.LocalDate
import javax.persistence.EntityManager
import javax.persistence.criteria.Predicate

@Repository
class EventRepositoryCustomImpl (
   private val em: EntityManager
) : EventRepositoryCustom {

    override fun findUnfinalizedEventsAfterOrEqual(
        filterEventsRequest: FilterEventsRequest,
        instrumentsId: List<Long>
    ): List<EventSearchDto> {
        val cb = em.criteriaBuilder
        val cq = cb.createQuery(Event::class.java)
        val root = cq.from(Event::class.java)
        cq.multiselect(
            root.get<Long>("id"),
            root.get<String>("imageUrl"),
            root.get<LocalDate>("eventDate")
        ).distinct(true)

        val joinExec = root.join<Event, EventJob>("eventJob")

        val predicates = mutableListOf<Predicate>()
        predicates.add(cb.equal(root.get<Boolean>("finalized"), false))
        predicates.add(cb.greaterThan(root.get<LocalDate>("eventDate"), LocalDate.now()))

        if (filterEventsRequest.date != null) {
            predicates.add(cb.equal(root.get<LocalDate>("eventDate"), filterEventsRequest.date))
        }

        if (filterEventsRequest.payment != null) {
            predicates.add(cb.greaterThanOrEqualTo(joinExec.get<Double>("payment"), filterEventsRequest.payment))
        }

        if (filterEventsRequest.instrumentsId != null) {
            predicates.add(joinExec.get<Long>("instrument").`in`(filterEventsRequest.instrumentsId))
        }

        else {
            predicates.add(joinExec.get<Long>("instrument").`in`(instrumentsId))
        }

        cq.where(*predicates.toTypedArray())

        val result = em.createQuery(cq).resultList

        return result.map { EventSearchDto(id = it.id, imageUrl = it.imageUrl, eventDate = it.eventDate, cep = it.cep) }
    }
}