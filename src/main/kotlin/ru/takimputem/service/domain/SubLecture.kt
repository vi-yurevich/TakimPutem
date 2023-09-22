package ru.takimputem.service.domain

import ru.takimputem.dao.enums.EventFormat
import ru.takimputem.dao.enums.EventType
import ru.takimputem.dao.model.EventEntity
import ru.takimputem.service.dto.EventDto
import java.math.BigDecimal

class SubLecture(
    id: Int,
    title: String,
    description: String,
    tags: String,
    imagesLinks: Set<String>,
    videosLinks: Set<String>,
    eventFormat: EventFormat,
    separateSale: Boolean,
    priceOnline: BigDecimal,
    priceOffline: BigDecimal,
    recordSale: Boolean,
    recordPrice: BigDecimal,
    videoRecordingsLinks: Set<String>,
): SubEvent(
    id,
    title,
    description,
    tags,
    imagesLinks,
    videosLinks,
    eventFormat,
    separateSale,
    priceOnline,
    priceOffline,
    recordSale,
    recordPrice,
    videoRecordingsLinks,
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