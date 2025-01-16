package com.componentware.wasserdatabase.controller;

import com.componentware.wasserdatabase.entity.Auftrag;
import com.componentware.wasserdatabase.entity.AuftragType;
import com.componentware.wasserdatabase.entity.Produkt;
import com.componentware.wasserdatabase.service.ProduktService;
import com.componentware.wasserdatabase.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produkt")
public class ProduktController {

    @Autowired
    private TransportService transportService;
    @Autowired
    private ProduktService produktService;
    @PostMapping("/process")
    public String processAuftrag(@RequestBody Auftrag auftrag) {
        if (auftrag.getType().equals(AuftragType.TRANSPORT.name())) {
            return "Erreur : Seuls les Aufträge de type 'TRANSPORT' sont acceptés.";
        }
        try {
            transportService.auftragsberarbeiten(auftrag);
            return "Update Auftrag: " + auftrag;
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
// Ajouter un nouveau produit
@PostMapping("/add")
public Produkt addProdukt(@RequestBody Produkt produkt) {
    return produktService.addProdukt(produkt);
}

// Modifier un produit existant
@PutMapping("/update/{id}")
public Produkt updateProdukt(@PathVariable Long id, @RequestBody Produkt produkt) {
    return produktService.updateProdukt(id, produkt);
}

// Supprimer un produit par ID
@DeleteMapping("/delete/{id}")
public String deleteProdukt(@PathVariable Long id) {
    produktService.deleteProdukt(id);
    return "Produit supprimé avec succès, ID: " + id;
}

// Récupérer un produit par ID
@GetMapping("/{id}")
public Produkt getProduktById(@PathVariable Long id) {
    return produktService.getProduktById(id);
}
// Récupérer un produit par ID
@GetMapping("/allprodukt")
public List<Produkt> getAllProdukt() {
    return produktService.getAllProdukt();
}}
//ajouter une methode pour ajouter,supprimer,modifier un product en fonction du id

