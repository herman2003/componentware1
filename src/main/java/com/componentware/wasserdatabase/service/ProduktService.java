package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.entity.Produkt;
import com.componentware.wasserdatabase.repository.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduktService {

    @Autowired
    private ProduktRepository produktRepository;

    // Ajouter un nouveau produit
    public Produkt addProdukt(Produkt produkt) {
        return produktRepository.save(produkt);
    }

    // Modifier un produit existant
    public Produkt updateProdukt(Long id, Produkt updatedProdukt) {
        Optional<Produkt> existingProdukt = produktRepository.findById(id);
        if (existingProdukt.isPresent()) {
            Produkt produkt = existingProdukt.get();
            produkt.setName(updatedProdukt.getName());
            produkt.setPreis(updatedProdukt.getPreis());
            return produktRepository.save(produkt);
        } else {
            throw new RuntimeException("Produkt non trouvé avec l'id: " + id);
        }
    }

    // Supprimer un produit par ID
    public void deleteProdukt(Long id) {
        if (produktRepository.existsById(id)) {
            produktRepository.deleteById(id);
        } else {
            throw new RuntimeException("Produkt non trouvé avec l'id: " + id);
        }
    }

    // Récupérer un produit par ID
    public Produkt getProduktById(Long id) {
        return produktRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Produkt non trouvé avec l'id: " + id)
        );
    }
    public List<Produkt> getAllProdukt() {
        return produktRepository.findAll();
    }
}
