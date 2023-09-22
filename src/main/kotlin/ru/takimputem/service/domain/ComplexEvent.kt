package ru.takimputem.service.domain

import java.math.BigDecimal

abstract class ComplexEvent(
    id: Int,
    title: String,
    description: String,
    tags: String,
    imagesLinks: Set<String>,
    videosLinks: Set<String>,
    val priceOnline: BigDecimal,
    val priceOffline: BigDecimal,
    val discountOffline: BigDecimal,
    val discountOnline: BigDecimal,
    val recordSale: Boolean,
    val recordPrice: BigDecimal,
    val recordDiscount: BigDecimal,
    val subEvents: List<SubEvent>,
): Event(
    id,
    title,
    description,
    tags,
    imagesLinks,
    videosLinks,
)