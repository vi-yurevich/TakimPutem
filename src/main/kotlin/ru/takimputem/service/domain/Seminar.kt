package ru.takimputem.service.domain

import ru.takimputem.dao.enums.EventFormat
import ru.takimputem.dao.enums.EventType
import ru.takimputem.dao.model.EventEntity
import ru.takimputem.service.dto.EventDto
import java.math.BigDecimal

class Seminar(
    id: Int,
    title: String,
    description: String,
    tags: String,
    imagesLinks: Set<String>,
    videosLinks: Set<String>,
    val eventFormat: EventFormat,
    val priceOnline: BigDecimal,
    val priceOffline: BigDecimal,
    val recordSale: Boolean,
    val recordPrice: BigDecimal,
    val videoRecordingsLinks: Set<String>,
): Event(
    id,
    title,
    description,
    tags,
    imagesLinks,
    videosLinks,
) {
    override fun toDto(): EventDto =
        EventDto(
            id = id,
            title = title,
            description = description,
            tags = tags,
            imagesLinks = imagesLinks,
            videosLinks = videosLinks,
            eventFormat = eventFormat,
            priceOnline = priceOnline,
            priceOffline = priceOffline,
            recordSale = recordSale,
            recordPrice = recordPrice,
            videoRecordingsLinks = videoRecordingsLinks,
        )

    override fun toDao(): EventEntity =
        EventEntity(
            id = id,
            eventType = EventType.LECTURE,
            title = title,
            description = description,
            tags = tags,
            imagesLinks = imagesLinks,
            videosLinks = videosLinks,
            eventFormat = eventFormat,
            priceOnline = priceOnline,
            priceOffline = priceOffline,
            recordSale = recordSale,
            recordPrice = recordPrice,
            videoRecordingsLinks = videoRecordingsLinks,
        )
}