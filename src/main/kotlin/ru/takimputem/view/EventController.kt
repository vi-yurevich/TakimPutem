package ru.takimputem.view

import org.springframework.web.bind.annotation.*
import ru.takimputem.dao.enums.EventFormat
import ru.takimputem.dao.enums.EventType
import ru.takimputem.dao.model.EventEntity
import ru.takimputem.service.EventService
import ru.takimputem.service.dto.EventDto

import java.math.BigDecimal

@RestController
class EventController(private val eventService: EventService) {
    @GetMapping("/event/all")
    fun getAllEvents(): List<EventDto> =
        eventService.findAll()

    @GetMapping("/event/{event_id}")
    fun getEventById(@PathVariable("event_id") eventId: Int): EventDto =
        eventService.findById(eventId)

    @PostMapping("/event")
    fun createEvent(@RequestBody eventDto: EventDto): EventDto =
        eventService.create(eventDto)

    @PutMapping("/update/event")
    fun updateEvent(@RequestBody eventDto: EventDto) =
        eventService.update(eventDto)
}

