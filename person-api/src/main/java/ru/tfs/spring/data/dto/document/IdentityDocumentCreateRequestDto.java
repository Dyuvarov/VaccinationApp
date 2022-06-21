package ru.tfs.spring.data.dto.document;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.data.type.DocumentType;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class IdentityDocumentCreateRequestDto {
    /** Вид документа */
    @NotNull
    private DocumentType    documentType;

    /** Номер */
    @NotNull
    private Integer         number;

    /** Основной документ */
    private Boolean         mainDoc;
}
