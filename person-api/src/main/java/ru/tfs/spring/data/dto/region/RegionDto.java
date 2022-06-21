package ru.tfs.spring.data.dto.region;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.data.dto.CommonDto;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegionDto extends CommonDto {
    /** Название региона */
    @NotNull
    private String  name;
}
