package ru.takimputem.dao.model

import jakarta.persistence.*
import jakarta.persistence.CascadeType
import jakarta.persistence.EnumType.*
import jakarta.persistence.Table
import org.hibernate.annotations.*
import ru.takimputem.dao.enums.*
import java.math.BigDecimal

@Entity
@Table(name = "event")
class EventEntity(
    @Enumerated(STRING)
    @Column(nullable = false)
    val eventType: EventType,

    @Enumerated(STRING)
    @Column(nullable = false)
    val eventFormat: EventFormat,

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String,

    @Column(columnDefinition = "TEXT", nullable = false)
    @ColumnDefault("''")
    val description: String = "",

    @Column(columnDefinition = "TEXT", nullable = false)
    @ColumnDefault("''")
    val tags: String = "",

    @ColumnDefault("true")
    @Column(nullable = false)
    val separateSale: Boolean = true,

    @ColumnDefault("0.00")
    @Column(nullable = false)
    val priceOnline: BigDecimal = BigDecimal(0),

    @ColumnDefault("0.00")
    @Column(nullable = false)
    val priceOffline: BigDecimal = BigDecimal(0),

    @ColumnDefault("0.00")
    @Column(nullable = false)
    val discountOnline: BigDecimal = BigDecimal(0),

    @ColumnDefault("0.00")
    @Column(nullable = false)
    val discountOffline: BigDecimal = BigDecimal(0),

    @ColumnDefault("false")
    @Column(nullable = false)
    val recordSale: Boolean = false,

    @ColumnDefault("0.00")
    @Column(nullable = false)
    val recordPrice: BigDecimal = BigDecimal(0),

    @ColumnDefault("0.00")
    @Column(nullable = false)
    val recordDiscount: BigDecimal = BigDecimal(0),

    @ElementCollection
    @CollectionTable(
        name = "event_image",
        joinColumns = [JoinColumn(name = "event_id")]
    )
    @Column(
        columnDefinition = "TEXT",
        nullable = false,
        name = "link")
    val imagesLinks: Set<String> = emptySet(),

    @ElementCollection
    @CollectionTable(
        name = "event_video",
        joinColumns = [JoinColumn(name = "event_id")]
    )
    @Column(
        columnDefinition = "TEXT",
        nullable = false,
        name = "link")
    val videosLinks: Set<String> = emptySet(),

    @ElementCollection
    @CollectionTable(
        name = "event_video_record",
        joinColumns = [JoinColumn(name = "event_id")]
    )
    @Column(
        columnDefinition = "TEXT",
        nullable = false,
        name = "link")
    val videoRecordingsLinks: Set<String> = emptySet(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "course_id",
        referencedColumnName = "id",
    )
    val parentEventEntity: EventEntity? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "course_id")
    val subEvents: List<EventEntity> = emptyList(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    )