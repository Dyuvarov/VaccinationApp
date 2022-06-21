package ru.tfs.spring.data.dto.person;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.data.dto.address.AddressCreateRequestDto;
import ru.tfs.spring.data.dto.contact.ContactCreateRequestDto;
import ru.tfs.spring.data.dto.document.IdentityDocumentCreateRequestDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class PersonCreateRequestDto {

    /** Признак необходимости скрытия пользователя из отображаемых списков */
    private Boolean     hidden=false;

    /** Имя */
    @NotBlank
    private String      firstName;

    /** Фамилия */
    @NotBlank
    private String      lastName;

    /** Отчество */
    private String      patronymic;

    /** Дата рождения */
    @NotNull
    private LocalDate   birthDate;

    /** Адрес регистрации */
    @NotNull
    private AddressCreateRequestDto        registryAddress;

    /** Адреса проживания */
    private Set<AddressCreateRequestDto>   addresses;

    /** Документы, удостоверяющие личность */
    @NotNull
    private Set<IdentityDocumentCreateRequestDto>   identityDocuments;

    /** Контакты */
    @NotNull
    private Set<ContactCreateRequestDto>   contacts;
}
