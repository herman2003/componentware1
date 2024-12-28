package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.entity.Auftrag;
import com.componentware.wasserdatabase.entity.Produkt;
import com.componentware.wasserdatabase.repository.AuftragRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbrechnungService {
    @Autowired
    private AuftragRepository auftragRepository;
    public void checkAuftrag(Auftrag auftrag, List<Produkt> produkts){
        float totalPreis = 0;
        for (Produkt produkt : produkts) {
            totalPreis += (float) produkt.getPreis();
        }

        auftrag.setPreis(totalPreis);

        auftragRepository.save(auftrag);

        System.out.println("Update vom Preis mit Auftrag ID" + auftrag.getId() + ": " + totalPreis);
    }
}
