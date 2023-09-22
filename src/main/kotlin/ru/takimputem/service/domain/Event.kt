package ru.takimputem.service.domain

import ru.takimputem.dao.model.EventEntity
import ru.takimputem.service.dto.EventDto

abstract class Event(
    val id: Int,
    val title: String,
    val description: String,
    val tags: String,
    val imagesLinks: Set<String>,
    val videosLinks: Set<String>,
) {
    abstract fun toDto(): EventDto

    abstract fun toDao(): EventEntity
}