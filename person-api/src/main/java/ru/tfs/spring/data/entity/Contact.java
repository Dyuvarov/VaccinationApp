package ru.tfs.spring.data.entity;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.data.type.ContactType;

import javax.persistence.*;

/** Контактная информация */
@Entity
@Table(name="t_contact")
@Getter
@Setter
public class Contact {
    /** Идентификатор контакта */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long        id;

    /** Вид контакта */
    @Enumerated(EnumType.STRING)
    private ContactType type;

    /** Контактные данные */
    private String      value;

    /** Владелец контакта */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "person_id", referencedColumnName = "id")
    private Person      person;
}
