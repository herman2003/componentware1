package com.componentware.wasserdatabase.controller;

import com.componentware.wasserdatabase.entity.Auftrag;
import com.componentware.wasserdatabase.entity.AuftragType;
import com.componentware.wasserdatabase.entity.User;
import com.componentware.wasserdatabase.service.TransportService;
import com.componentware.wasserdatabase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private TransportService transportService;
    @Autowired
    private UserService userService;
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
    @GetMapping("/info/{email}")
    public User getUserInfo(@PathVariable String email) {
        // Récupérer l'utilisateur par email
        User user = userService.findByEmail(email);  // Implémenter findByEmail dans UserService
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé avec l'email: " + email);
        }
        return user;
    }
}

