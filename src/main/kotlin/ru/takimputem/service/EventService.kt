package ru.takimputem.service

import org.springframework.stereotype.Service
import ru.takimputem.dao.model.EventEntity
import ru.takimputem.dao.repo.EventRepository
import ru.takimputem.service.dto.EventDto
import ru.takimputem.view.exceptions.EventNotFoundException
import java.util.*

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val eventFactory: EventFactory
) {

    fun findAll(): List<EventDto> {
        val eventDaoList = eventRepository.findAll().toList()
        val eventList = eventDaoList.map { eventFactory.newEventFromDao(it) }
        return eventList.map { it.toDto() }
    }

    fun findById(id: Int): EventDto {
        val result: Optional<EventEntity> = eventRepository.findById(id)
        if (result.isPresent) {
            val foundEvent = eventFactory.newEventFromDao(result.get())
            return foundEvent.toDto()
        }
        else
            throw EventNotFoundException("Ивент с id = $id найден")
    }

    fun findByIdDao(id: Int): EventEntity {
        val result: Optional<EventEntity> = eventRepository.findById(id)
        if (result.isPresent) {
            return result.get()
        }
        else
            throw EventNotFoundException("Ивент с id = $id найден")
    }

    fun create(eventDto: EventDto): EventDto {
        val event = eventFactory.newEventFromDto(eventDto)
        val eventDao = event.toDao()
        val createdDao = eventRepository.save(eventDao)
        val created = eventFactory.newEventFromDao(createdDao)
        return created.toDto()
    }

    fun update(eventDto: EventDto): Unit {
        if (eventDto.id == null || eventDto.id <= 0)
            throw IllegalArgumentException("Указано неверное значение для параметра id = $eventDto.id.")

        val updatableDao = findByIdDao(eventDto.id)
        val updatedEventDto = eventFactory.createDtoForUpdate(eventDto, updatableDao)
        val updatedEvent = eventFactory.newEventFromDto(updatedEventDto)
        val updatedDao = updatedEvent.toDao()
        eventRepository.save(updatedDao)
    }
}

