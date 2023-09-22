package ru.takimputem.service.domain

import ru.takimputem.dao.enums.EventFormat
import ru.takimputem.dao.enums.EventType
import ru.takimputem.dao.model.EventEntity
import ru.takimputem.service.dto.EventDto
import java.math.BigDecimal

class Course(
    id: Int,
    title: String,
    description: String,
    tags: String,
    imagesLinks: Set<String>,
    videosLinks: Set<String>,
    priceOnline: BigDecimal,
    priceOffline: BigDecimal,
    discountOffline: BigDecimal,
    discountOnline: BigDecimal,
    recordSale: Boolean,
    recordPrice: BigDecimal,
    recordDiscount: BigDecimal,
    subEvents: List<SubEvent>,
): ComplexEvent(
    id,
    title,
    description,
    tags,
    imagesLinks,
    videosLinks,
    priceOnline,
    priceOffline,
    discountOffline,
    discountOnline,
    recordSale,
    recordPrice,
    recordDiscount,
    subEvents,
) {
    override fun toDto(): EventDto {
        val subEventsDto = subEvents.map { it.toDto() }
        return EventDto(
            id = id,
            title = title,
            description = description,
            tags = tags,
            imagesLinks = imagesLinks,
            videosLinks = videosLinks,
            priceOnline = priceOnline,
            priceOffline = priceOffline,
            discountOnline = discountOnline,
            discountOffline = discountOffline,
            recordSale = recordSale,
            recordPrice = recordPrice,
            recordDiscount = recordDiscount,
            subEvents = subEventsDto,
        )
    }

    override fun toDao(): EventEntity {
        val subEventsDao = subEvents.map { it.toDao() }
        return EventEntity(
            id = id,
            eventType = EventType.COURSE,
            eventFormat = EventFormat.ONLINE,
            title = title,
            description = description,
            tags = tags,
            imagesLinks = imagesLinks,
            videosLinks = videosLinks,
            priceOnline = priceOnline,
            priceOffline = priceOffline,
            discountOnline = discountOnline,
            discountOffline = discountOffline,
            recordSale = recordSale,
            recordPrice = recordPrice,
            recordDiscount = recordDiscount,
            subEvents = subEventsDao,
        )
    }
}