package com.componentware.wasserdatabase.repository;
import com.componentware.wasserdatabase.entity.Nachricht;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface NachrichtRepository extends CrudRepository<Nachricht, Long> {

}
