package ru.tfs.spring.data.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.spring.data.entity.IdentityDocument;
import ru.tfs.spring.data.type.DocumentType;

import java.util.Optional;

@Repository
public interface IdentityDocumentRepository extends JpaRepository<IdentityDocument, Long> {
    boolean existsByMainDocIsTrueAndPersonId(Long personId);

    @EntityGraph(attributePaths = "person", type = EntityGraph.EntityGraphType.LOAD)
    Optional<IdentityDocument> findIdentityDocumentByDocumentTypeAndNumber(DocumentType type, Integer number);
}
