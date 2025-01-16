package com.componentware.wasserdatabase.repository;

import com.componentware.wasserdatabase.entity.Sender;
import com.componentware.wasserdatabase.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SenderRepository extends CrudRepository<Sender,Integer> {
    Sender findTopByOrderByIdDesc();

    List<Sender> findSendersByUser(User user);
}
