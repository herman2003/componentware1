package com.componentware.wasserdatabase.controller;

import com.componentware.wasserdatabase.entity.Auftrag;
import com.componentware.wasserdatabase.entity.AuftragType;
import com.componentware.wasserdatabase.entity.Nachricht;
import com.componentware.wasserdatabase.service.AuftragsService;
import com.componentware.wasserdatabase.service.NachrichtService;
import com.componentware.wasserdatabase.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auftrag1")
public class AuftragController {
    @Autowired
    private NachrichtService nachrichtService;
    @Autowired
    private TransportService transportService;
    @Autowired
    private AuftragsService auftragService;
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
    @GetMapping("/user/{userId}")
    public List<Auftrag> getAuftrageByUser(@PathVariable Long userId) {//bagination von webpage
        return auftragService.getAuftragByUserId(userId);
    }
    @GetMapping("/type/{type}") //
    public List<Auftrag> getAuftrageByType(@PathVariable String type) {
        return auftragService.getAuftragByType(type);
    }

    //getalle Aufträge
    @GetMapping("/all")
    public List<Auftrag> getAllAuftraege(){
        return auftragService.findAllAuftraege();
    }
    @GetMapping("/allnachricht")
    public List<Nachricht> getAllNachrichten(){
        return nachrichtService.findAll();
    }
    @GetMapping("/sender/{senderId}")
    public Nachricht getNachrichtenBySender(@PathVariable(required = false) Long senderId) {
        if (senderId == null) {
            senderId = 1L;
        }

        // Utiliser le service pour récupérer les Nachrichten liées à ce Sender
        List<Nachricht> nachrichten = nachrichtService.findBySender(senderId);


        // Retourner la liste des Nachrichten
        return nachrichten.stream().findFirst().orElse(null);
    }
}

