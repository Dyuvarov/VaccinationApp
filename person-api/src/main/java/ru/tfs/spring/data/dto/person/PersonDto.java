package ru.tfs.spring.data.dto.person;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.data.dto.CommonDto;
import ru.tfs.spring.data.dto.address.AddressDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class PersonDto extends CommonDto {
    /** Признак необходимости скрытия пользователя из отображаемых списков */
    @NotNull
    private Boolean                     hidden;

    /** Имя */
    @NotNull
    private String                      firstName;

    /** Фамилия */
    @NotNull
    private String                      lastName;

    /** Отчество */
    private String                      patronymic;

    /** Дата рождения */
    @NotNull
    private LocalDate                   birthDate;

    /** Адрес регистрации */
    @NotNull
    private AddressDto                  registryAddress;

    /** Адреса проживания */
    private Set<AddressDto>             addresses;
}
