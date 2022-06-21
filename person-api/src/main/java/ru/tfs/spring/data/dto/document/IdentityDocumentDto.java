package ru.tfs.spring.data.dto.document;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.data.dto.CommonDto;
import ru.tfs.spring.data.type.DocumentType;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class IdentityDocumentDto extends CommonDto {

    /** Вид документа */
    @NotNull
    private DocumentType    documentType;

    /** Серия */
    private Integer         serialNumber;

    /** Номер */
    @NotNull
    private Integer         number;

    /** Основной документ */
    private Boolean         mainDoc;

    /** Идентификатор владельца документа */
    @NotNull
    private Long            personId;

    @Override
    public String toString() {
        return String.format("%s: номер: %d", documentType.getTypeName(), number);
    }
}
