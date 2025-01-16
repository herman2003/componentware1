package com.componentware.wasserdatabase.controller;

import com.componentware.wasserdatabase.entity.Auftrag;
import com.componentware.wasserdatabase.entity.AuftragType;
import com.componentware.wasserdatabase.entity.Nachricht;
import com.componentware.wasserdatabase.entity.Sender;
import com.componentware.wasserdatabase.service.NachrichtService;
import com.componentware.wasserdatabase.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nachricht")
public class NachrichtController {
    @Autowired
    private NachrichtService nachrichtService; // Service pour gérer les Nachrichten
    @Autowired
    private TransportService transportService;
    // Récupérer toutes les Nachrichten associées à un Sender par son ID
    @GetMapping("/sender/{senderId}")
    public Nachricht getNachrichtenBySender(@PathVariable(required = false) Long senderId) {
        if (senderId == null) {
            senderId = 1L;
        }
        // Récupérer le Sender par son ID
        Sender sender = new Sender();
        sender.setId(senderId);

        // Utiliser le service pour récupérer les Nachrichten liées à ce Sender
        List<Nachricht> nachrichten = nachrichtService.findBySender(sender);


        // Retourner la liste des Nachrichten
        return nachrichten.stream().findFirst().orElse(null);
    }

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
}

