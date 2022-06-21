package ru.tfs.spring.data.dto.person;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.data.dto.CommonDto;

import java.time.LocalDate;

/** Для вывода списка. Dto отражает состав полей одной строки*/
@Getter
@Setter
public class PersonJournalDto extends CommonDto {

    /** Имя */
    private String      firstName;

    /** Фамилия */
    private String      lastName;

    /** Отчество */
    private String      patronymic;

    /** Дата рождения */
    private LocalDate   birthDate;

    /** Номер телефона */
    private String      phoneNumber;

    /** Данные об основном документе */
    private String      mainDocument;

    /** Адрес регистрации */
    private String      registryAddress;
}
