package ru.takimputem.service.domain

import ru.takimputem.dao.enums.EventFormat
import java.math.BigDecimal

class SubSeminar(
    id: Int,
    title: String,
    description: String,
    tags: String,
    imagesLinks: Set<String>,
    videosLinks: Set<String>,
    format: EventFormat,
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
    format,
    separateSale,
    priceOnline,
    priceOffline,
    recordSale,
    recordPrice,
    videoRecordingsLinks,
) {

}