package ru.tfs.spring.data.dto.address;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.data.dto.region.RegionDto;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressCreateRequestDto {
    /** Регион */
    @NotNull
    private RegionDto region;

    /** Название города */
    @NotNull
    private String  city;

    /** Название улицы */
    @NotNull
    private String  street;

    /** Номер дома */
    @NotNull
    private Integer house;

    /** Номер квартиры */
    private Integer flat;
}
