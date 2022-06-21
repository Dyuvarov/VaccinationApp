package ru.tfs.spring.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.spring.data.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
