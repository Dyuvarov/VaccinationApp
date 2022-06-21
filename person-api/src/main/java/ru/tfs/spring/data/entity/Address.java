package ru.tfs.spring.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
@Table(name="t_address")
@Getter
@Setter
public class Address {
    /** Идентфикатор адреса */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;

    /** Регион */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "region_id", referencedColumnName = "id")
    private Region  region;

    /** Название города */
    private String  city;

    /** Название улицы */
    private String  street;

    /** Номер дома */
    private Integer house;

    /** Номер квартиры */
    private Integer flat;
}
