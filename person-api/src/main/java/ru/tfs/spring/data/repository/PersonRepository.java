package ru.tfs.spring.data.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tfs.spring.data.entity.Person;
import ru.tfs.spring.data.type.DocumentType;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	@Query("select p from Person p where p.hidden=false")
	List<Person> findNotHiddenWithPageable(Pageable pageable);

	@Query("select p from Person p where p.hidden=false and p.registryAddress.region.name=?1")
	List<Person> findNotHiddenWithPageableByRegion(String regionName, Pageable pageable);

	@Query("select p from Person p join p.identityDocuments d " +
			"where p.firstName=?1 and p.lastName=?2 and p.patronymic=?3 and d.number=?4")
	Optional<Person> findByFIOAndDocumentNumber(String name, String lastName, String patronymic, Integer document);
}
