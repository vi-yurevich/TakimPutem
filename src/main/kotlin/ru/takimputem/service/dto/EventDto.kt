package ru.takimputem.service.dto

import ru.takimputem.dao.enums.EventFormat
import ru.takimputem.dao.enums.EventType
import java.math.BigDecimal

data class
EventDto(
    val eventType: EventType? = null,
    val eventFormat: EventFormat? = null,
    val title: String? = null,
    val tags: String? = null,
    val description: String? = null,
    val separateSale: Boolean? = null,
    val priceOnline: BigDecimal? = null,
    val priceOffline: BigDecimal? = null,
    val discountOnline: BigDecimal? = null,
    val discountOffline: BigDecimal? = null,
    val recordSale: Boolean? = null,
    val recordPrice: BigDecimal? = null,
    val recordDiscount: BigDecimal? = null,
    val imagesLinks: Set<String>? = null,
    val videosLinks: Set<String>? = null,
    val videoRecordingsLinks: Set<String>? = null,
    val parentEvent: Int? = null,
    val subEvents: List<EventDto>? = null,
    val id: Int? = null,
    )
