package ru.takimputem.service

import org.springframework.stereotype.Service
import ru.takimputem.dao.enums.EventFormat.*
import ru.takimputem.dao.enums.EventType.*
import ru.takimputem.dao.model.EventEntity
import ru.takimputem.service.domain.*
import ru.takimputem.service.dto.EventDto
import ru.takimputem.view.exceptions.ValidateDtoException
import java.math.BigDecimal

@Service
class EventFactory {
    fun newEventFromDao(eventEntity: EventEntity): Event =
        when(eventEntity.eventType) {
            LECTURE -> newLecture(eventEntity)
            SEMINAR -> newSeminar(eventEntity)
            COURSE ->  newCourse(eventEntity)
            LECTURE_IN_THE_COURSE, SEMINAR_IN_THE_COURSE -> newSubEventFromDao(eventEntity)
            else -> throw IllegalArgumentException(
                "Попытка создать Event из DAO с типом ивента: ${eventEntity.eventType} функционал создания, которого ещё не разработан."
            )
        }

    fun newEventFromDto(eventDto: EventDto): Event =
        when(eventDto.eventType) {
            LECTURE -> newLecture(eventDto)
            else -> throw IllegalArgumentException(
                "Попытка создать Event из DTO с типом ивента: ${eventDto.eventType} функционал создания, которого ещё не разработан."
            )
        }

    fun newSubEventFromDao(eventEntity: EventEntity): SubEvent =
        when(eventEntity.eventType) {
            LECTURE_IN_THE_COURSE -> newSubLecture(eventEntity)
            SEMINAR_IN_THE_COURSE -> newSubSeminar(eventEntity)
            else -> throw IllegalArgumentException(
                "Попытка создать SubEvent из DAO с типом ивента: ${eventEntity.eventType}"
            )
        }

    fun createDtoForUpdate(eventDto: EventDto, eventEntity: EventEntity): EventDto {
        val newSubEventsDto = eventDto.subEvents
            ?: eventEntity.subEvents
                .map { newEventFromDao(it) }
                .map { it.toDto() }

        return with(eventDto) {
            EventDto(
                eventType ?: eventEntity.eventType,
                eventFormat ?: eventEntity.eventFormat,
                title ?: eventEntity.title,
                tags ?: eventEntity.tags,
                description ?: eventEntity.description,
                separateSale ?: eventEntity.separateSale,
                priceOnline ?: eventEntity.priceOnline,
                priceOffline ?: eventEntity.priceOffline,
                discountOnline ?: eventEntity.discountOnline,
                discountOffline ?: eventEntity.discountOffline,
                recordSale ?: eventEntity.recordSale,
                recordPrice ?: eventEntity.recordPrice,
                recordDiscount ?: eventEntity.recordDiscount,
                imagesLinks ?: eventEntity.imagesLinks,
                videosLinks ?: eventEntity.videosLinks,
                videoRecordingsLinks ?: eventEntity.videoRecordingsLinks,
                parentEvent ?: eventEntity.parentEventEntity?.id,
                newSubEventsDto,
                id ?: eventEntity.id,
            )
        }
    }

    fun newLecture(eventEntity: EventEntity): Lecture =
        with(eventEntity) {
            Lecture(
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
        }

    fun newLecture(eventDto: EventDto): Lecture =
        with(eventDto) {
            val errorsDescription = StringBuilder()

            val eventFormat = if (eventFormat == null) {
                errorsDescription.append("\nНе указан формат проведения мероприятия.")
                OFFLINE
            } else {
                eventFormat
            }

            val tags = ""

            val title = if (title == null) {
                errorsDescription.append("\nНе указано название мероприятия.")
                ""
            } else
                title.trim()

            val description = if (description == null) {
                errorsDescription.append("\nНе указано название мероприятия.")
                ""
            } else
                description.trim()

            if (separateSale == false) {
                errorsDescription.append(
                    "\nДля самостоятельной лекции указан статус отдельной продажи."
                )
            }

            val priceOnline = if (eventFormat == ONLINE || eventFormat == MIXED) {
                if (priceOnline == null) {
                    errorsDescription.append("\nНе указана цена участия онлайн.")
                    BigDecimal(0)
                } else if (priceOnline < BigDecimal(0)) {
                    errorsDescription.append("\nЦена участия онлайн не должна быть меньше нуля.")
                    BigDecimal(0)
                } else
                    priceOnline
            } else {
                if (priceOnline == BigDecimal(0)) {
                    errorsDescription.append("\nУказана цена онлайн участия для мероприятия с форматом OFFLINE.")
                }
                BigDecimal(0)
            }

            val priceOffline = if (eventFormat == OFFLINE || eventFormat == MIXED) {
                if (priceOffline == null) {
                    errorsDescription.append("\nНе указана цена очного участия.")
                    BigDecimal(0)
                } else if (priceOffline < BigDecimal(0)) {
                    errorsDescription.append("\nЦена очного участия не должна быть меньше нуля.")
                    BigDecimal(0)
                } else
                    priceOffline
            } else {
                if (priceOffline != null) {
                    errorsDescription.append("\nУказана цена очного участия для мероприятия с форматом ONLINE")
                }
                BigDecimal(0)
            }

            val recordSale = if (recordSale == null) {
                errorsDescription.append("\nНе указан идентификатор открытой продажи записи мероприятия.")
                false
            } else
                recordSale

            val recordPrice = if (recordPrice == null) {
                errorsDescription.append("""|
                    |Не указана стоимость доступа к записи мероприятия. 
                    |Стоимость доступа должна быть указана явно как 0, даже в том случае, если запись не продаётся.
                    |""".trimMargin()
                )
                BigDecimal(0)
            } else if (this.recordSale == false && recordPrice > BigDecimal(0)) {
                errorsDescription.append(
                    "\nУказана стоимость доступа к записи мероприятию при том, что запись помечена как недоступная к продаже."
                )
                BigDecimal(0)
            } else if (recordPrice < BigDecimal(0)) {
                errorsDescription.append("\nСтоимость доступа к записи не может быть меньше нуля.")
                BigDecimal(0)
            } else
                recordPrice

            val imagesLinks = emptySet<String>()
            val videosLinks = emptySet<String>()
            val videoRecordingsLinks = emptySet<String>()
            val id = id ?: 0

            if (errorsDescription.isEmpty()) {
                Lecture(
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
            } else {
                errorsDescription.insert(
                    0,
                    "В процессе создания объекта Lecture из DTO были обнаружены следующие ошибки:"
                )
                throw ValidateDtoException(errorsDescription.toString())
            }
        }


    fun newSeminar(eventEntity: EventEntity): Seminar =
        with(eventEntity) {
            Seminar(
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
        }

    fun newCourse(eventEntity: EventEntity): Course =
        with(eventEntity) {
            val subEventList = subEvents
                .map { newSubEventFromDao(it) }

            Course(
                id = id,
                title = title,
                description = description,
                tags = tags,
                imagesLinks = imagesLinks,
                videosLinks = videosLinks,
                priceOnline = priceOnline,
                priceOffline = priceOffline,
                discountOffline = discountOffline,
                discountOnline = discountOnline,
                recordSale = recordSale,
                recordPrice = recordPrice,
                recordDiscount = recordDiscount,
                subEvents = subEventList,
            )
        }

    fun newSubLecture(eventEntity: EventEntity): SubLecture =
        with(eventEntity) {
            SubLecture(
                id = id,
                title = title,
                description = description,
                tags = tags,
                imagesLinks = imagesLinks,
                videosLinks = videosLinks,
                eventFormat = eventFormat,
                separateSale = separateSale,
                priceOnline = priceOnline,
                priceOffline = priceOffline,
                recordSale = recordSale,
                recordPrice = recordPrice,
                videoRecordingsLinks = videoRecordingsLinks,
            )
        }

    fun newSubSeminar(eventEntity: EventEntity): SubSeminar =
        with(eventEntity) {
            SubSeminar(
                id = id,
                title = title,
                description = description,
                tags = tags,
                imagesLinks = imagesLinks,
                videosLinks = videosLinks,
                format = eventFormat,
                separateSale = separateSale,
                priceOnline = priceOnline,
                priceOffline = priceOffline,
                recordSale = recordSale,
                recordPrice = recordPrice,
                videoRecordingsLinks = videoRecordingsLinks,
            )
        }
}