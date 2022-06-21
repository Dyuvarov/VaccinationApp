package ru.tfs.spring.data.type;

import lombok.Getter;

@Getter
public enum DocumentType {
    RUSSIAN_PASSPORT("Паспорт РФ"),
    INTERNATIONAL_PASSPORT("Заграничный паспорт"),
    MILITARY_ID("Военный билет");

    private String typeName;

    DocumentType(String name) {
        this.typeName = name;
    }
}
