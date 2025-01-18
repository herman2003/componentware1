package com.componentware.wasserdatabase.repository;

import com.componentware.wasserdatabase.entity.Auftrag;
import com.componentware.wasserdatabase.entity.Produkt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProduktRepository extends CrudRepository<Produkt, Long> {
    List<Produkt> findAll();
    Optional<Produkt> findById(Long id);
}
