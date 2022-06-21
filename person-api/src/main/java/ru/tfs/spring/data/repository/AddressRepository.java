package ru.tfs.spring.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tfs.spring.data.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a " +
            "WHERE a.region.id=?1 AND a.city=?2 AND a.street=?3 AND a.house=?4 AND a.flat=?5")
    Address findAddressByAllFields(Long regionId, String city, String street,
                                   Integer house, Integer flat);
}
