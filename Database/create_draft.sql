create table event
(
    id               serial
        constraint event_pk
            primary key,
    type             text                            not null,
    title            text                            not null,
    description      text           default ''::text not null,
    tags             text           default ''::text not null,
    separate_sale    boolean        default true     not null,
    format           text                            not null,
    price_online     numeric(12, 2) default 0.00     not null,
    price_offline    numeric(12, 2) default 0.00     not null,
    discount_offline numeric(12, 2) default 0.00     not null,
    discount_online  numeric(12, 2) default 0.00     not null,
    record_sale      boolean        default false    not null,
    record_price     numeric(12, 2) default 0.00     not null,
    record_discount  numeric(12, 2) default 0.00     not null,
    course_id        integer
        constraint event_event_id_fk
            references event
);

comment on column event.type is 'Тип проводимого эвента. Может принимать одно из следующих значений:
COURSE
LECTURE
LECTURE_IN_THE_COURSE
SEMINAR
SEMINAR_IN_THE_COURSE
DISCUSSION
BOOK_CLUB
MEETING
TRAVEL
TRAVEL_DAY';

comment on column event.format is 'Формат проводимого эвента. Может принимать одно из следующих значений:
ONLINE
OFFLINE
MIXED
NO_FORMAT';

alter table event
    owner to postgres;

create table event_video
(
    event_id integer not null
        constraint video_event_id_fk
            references event,
    link     text    not null,
    constraint video_pk
        primary key (event_id, link)
);

alter table event_video
    owner to postgres;

create table event_image
(
    event_id integer not null
        constraint images_event_id_fk
            references event,
    link     text    not null,
    constraint images_pk
        primary key (event_id, link)
);

alter table event_image
    owner to postgres;

create table person
(
    id                  serial
        constraint person_pk
            primary key,
    last_name           text                  not null,
    first_name          text                  not null,
    surname             text default ''::text not null,
    email               text default ''::text not null,
    phone_number        text                  not null,
    additional_contacts text default ''::text not null,
    actor_description   text default ''::text not null,
    comment             text default ''::text not null,
    telegram_username   text default ''::text not null,
    telegram_id         bigint
);

alter table person
    owner to postgres;

create table timetable
(
    id         serial
        constraint timetable_pk
            primary key,
    event_id   integer not null
        constraint timetable_event_id_fk
            references event,
    start_date date    not null,
    end_date   date    not null
);

alter table timetable
    owner to postgres;

create table actor_event
(
    actor_id integer not null
        constraint actor_event_person_id_fk
            references person,
    event_id integer not null
        constraint actor_event_event_id_fk
            references event,
    constraint actor_event_pk
        primary key (actor_id, event_id)
);

alter table actor_event
    owner to postgres;

create table event_video_record
(
    event_id integer not null
        constraint records_event_id_fk
            references event,
    link     text    not null,
    constraint records_pk
        primary key (event_id, link)
);

alter table event_video_record
    owner to postgres;

create table payment
(
    id           integer               not null
        constraint payment_pk
            primary key,
    timetable_id integer
        constraint payment_timetable_id_fk
            references timetable,
    person_id    integer
        constraint payment_person_id_fk
            references person,
    datetime     timestamp             not null,
    amount       numeric(12, 2)        not null,
    description  text default ''::text not null
);

alter table payment
    owner to postgres;

create table tags
(
    event_id integer not null
        constraint tags_event_id_fk
            references event,
    tag      text    not null,
    constraint tags_pk
        primary key (event_id, tag)
);

alter table tags
    owner to postgres;

create table discount
(
    id              integer not null
        constraint discount_pk
            primary key,
    event_id        integer
        constraint discount_event_id_fk
            references event,
    expiration_date integer,
    amount          numeric(12, 2)
);

alter table discount
    owner to postgres;

