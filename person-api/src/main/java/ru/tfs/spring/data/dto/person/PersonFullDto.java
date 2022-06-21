package ru.tfs.spring.data.dto.person;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.data.dto.address.AddressDto;
import ru.tfs.spring.data.dto.contact.ContactDto;
import ru.tfs.spring.data.dto.document.IdentityDocumentDto;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class PersonFullDto {
    private Long                        id;

    /** Признак необходимости скрытия пользователя из отображаемых списков */
    private Boolean                     hidden;

    /** Имя */
    private String                      firstName;

    /** Фамилия */
    private String                      lastName;

    /** Отчество */
    private String                      patronymic;

    /** Дата рождения */
    private LocalDate                   birthDate;

    /** Адрес регистрации */
    private AddressDto                  registryAddress;

    /** Адреса проживания */
    private Set<AddressDto>             addresses;

    /** Документы, удостоверяющие личность */
    private Set<IdentityDocumentDto>    identityDocuments;

    /** Контакты */
    private Set<ContactDto>             contacts;
}
