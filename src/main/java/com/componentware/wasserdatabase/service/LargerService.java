package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.entity.Produkt;
import com.componentware.wasserdatabase.repository.ProduktRepository;
import com.componentware.wasserdatabase.repository.SensorSenderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class LargerService {
    @Autowired
    private ProduktRepository produktRepository;
public boolean technickUeberpruefung(Long id){
   Optional<Produkt> produkt= produktRepository.findById(id);
    return produkt.isPresent();
}
}
