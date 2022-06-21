package ru.tfs.spring.data.dto.contact;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.data.type.ContactType;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ContactCreateRequestDto {
    /** Вид контакта */
    @NotNull
    private ContactType type;

    /** Контактные данные */
    @NotNull
    private String      value;
}
