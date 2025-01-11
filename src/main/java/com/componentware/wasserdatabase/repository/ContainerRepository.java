package com.componentware.wasserdatabase.repository;

import com.componentware.wasserdatabase.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ContainerRepository extends CrudRepository<Container, Long> {
    Container getFirstById(Long id);
}
