package ru.takimputem.dao.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.takimputem.dao.model.EventEntity

@Repository
interface EventRepository : JpaRepository<EventEntity, Int> {
}