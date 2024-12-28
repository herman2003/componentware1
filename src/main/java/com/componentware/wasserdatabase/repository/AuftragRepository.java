package com.componentware.wasserdatabase.repository;

import com.componentware.wasserdatabase.entity.Auftrag;
import com.componentware.wasserdatabase.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuftragRepository extends CrudRepository<Auftrag, Long> {
    Optional<Auftrag> findTopByTypeOrderByDateDesc(String type);
}
