package ru.tfs.spring.data.entity;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.data.type.DocumentType;

import javax.persistence.*;

@Entity
@Table(name="t_identity_document")
@Getter
@Setter
public class IdentityDocument {
    public static final String personGraph = "Document[Person]";

    /** Идентификатор документа */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long            id;

    /** Вид документа */
    @Enumerated(EnumType.STRING)
    @Column(name="document_type")
    private DocumentType documentType;

    /** Номер */
    private Integer         number;

    /** Основной документ */
    @Column(name = "main_doc")
    private Boolean         mainDoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "person_id", referencedColumnName = "id")
    private Person          person;
}
