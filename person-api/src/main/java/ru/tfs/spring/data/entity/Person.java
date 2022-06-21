package ru.tfs.spring.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="t_person")
@Getter
@Setter
public class Person {
    /** Идентификатор персоны */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long                    id;

    /** Признак необходимости скрытия пользователя из отображаемых списков */
    @Column(name="hidden")
    private Boolean                 hidden;

    /** Имя */
    @Column(name="first_name")
    private String                  firstName;

    /** Фамилия */
    @Column(name="last_name")
    private String                  lastName;

    /** Отчество */
    private String                  patronymic;

    /** Дата рождения */
    @Column(name="birth_date")
    private LocalDate               birthDate;

    /** Адрес регистрации */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(columnDefinition = "registry_address_id", referencedColumnName = "id")
    private Address                 registryAddress;

    /** Адреса проживания */
    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "t_person_address",
            joinColumns = @JoinColumn(name="person_id"),
            inverseJoinColumns = @JoinColumn(name="address_id")
    )
    private Set<Address>            addresses;

    /** Документы, удостоверяющие личность */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="person_id")
    private Set<IdentityDocument>   identityDocuments;

    /** Контакты */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="person_id")
    private Set<Contact>            contacts;
}
