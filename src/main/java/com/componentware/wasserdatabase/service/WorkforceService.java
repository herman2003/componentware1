package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.entity.Auftrag;
import com.componentware.wasserdatabase.entity.AuftragType;
import com.componentware.wasserdatabase.repository.AuftragRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@Service
public class WorkforceService {
    @Autowired
    private  AuftragRepository auftragRepository;

    public void auftragsberarbeiten(Auftrag auftrag) {
        Random random = new Random();

        Optional<Auftrag> existingAuftrag = auftragRepository.findById(auftrag.getId());
        if (existingAuftrag.isEmpty()) {
            throw new IllegalArgumentException("Auftrag mit dem ID " + auftrag.getId() + "existiert nicht im Datenbank");
        }

        Optional<Auftrag> letzterTransportAuftrag = auftragRepository.findTopByTypeOrderByDateDesc(AuftragType.WORKFORCE.name());

        LocalDateTime neueDatum;
        if (letzterTransportAuftrag.isPresent() && !letzterTransportAuftrag.get().getId().equals(auftrag.getId())) {

            neueDatum = letzterTransportAuftrag.get().getDate().plusHours(1);
        } else {

            neueDatum = LocalDateTime.now();
        }

        Auftrag auftragToUpdate = existingAuftrag.get();
        auftragToUpdate.setDate(neueDatum);
        auftragToUpdate.setType(AuftragType.WORKFORCE.name());
        auftragRepository.save(auftragToUpdate);

        System.out.println("Auftrag mis à jour : " + auftragToUpdate);
    }
}
