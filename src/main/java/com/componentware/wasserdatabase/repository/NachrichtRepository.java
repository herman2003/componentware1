package com.componentware.wasserdatabase.repository;
import com.componentware.wasserdatabase.entity.Nachricht;
import com.componentware.wasserdatabase.entity.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface NachrichtRepository extends CrudRepository<Nachricht, Long> {
    List<Nachricht> findAllBySender_Id(long sender_id);
    List<Nachricht> findNachrichtBySender(Sender sender);

    List<Nachricht> sender_id(Long senderId);
}
