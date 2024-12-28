package com.componentware.wasserdatabase.repository;

import com.componentware.wasserdatabase.entity.SensorSender;
import org.springframework.data.repository.CrudRepository;

public interface SensorSenderRepository extends CrudRepository<SensorSender,Integer> {
    SensorSender findTopByOrderByIdDesc();
}
