package ru.tfs.spring.data.dto.address;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.data.dto.region.RegionDto;

import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

@Getter
@Setter
public class AddressDto {
    protected Long id;

    /** Регион */
    @NotNull
    private RegionDto   region;

    /** Название города */
    @NotNull
    private String      city;

    /** Название улицы */
    @NotNull
    private String      street;

    /** Номер дома */
    @NotNull
    private Integer     house;

    /** Номер квартиры */
    private Integer     flat;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ");
        if (region != null && region.getName() != null) { sj.add(region.getName()); }
        if (city != null) { sj.add(city); }
        if (street != null) {sj.add("ул.: " + street); }
        if (house != null) { sj.add("д.: " + house); }
        if (flat != null) { sj.add("кв.: " + flat); }
        return sj.toString();
    }
}
