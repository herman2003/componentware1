package com.componentware.wasserdatabase.controller;

import com.componentware.wasserdatabase.entity.Sender;
import com.componentware.wasserdatabase.entity.User;
import com.componentware.wasserdatabase.repository.SenderRepository;
import com.componentware.wasserdatabase.service.SenderService;
import com.componentware.wasserdatabase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sender")
public class SenderController {
    @Autowired
    private SenderService senderService;
    @Autowired
    private UserService userService;
    @GetMapping("/user/{email}")
    public List<Sender> getSendersByUser(@PathVariable String email) {
        // Récupérer l'utilisateur par email
        User user = userService.findByEmail(email); // Méthode findByEmail dans UserService
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé avec l'email: " + email);
        }

        // Récupérer tous les senders associés à l'utilisateur
        return senderService.findSendersByUser(user);
    }
    @PostMapping("/add/{userId}")
    public Sender addSenderToUser(@PathVariable Long userId, @RequestBody Sender sender) {
        return senderService.addSenderToUser(userId, sender);
    }
    @DeleteMapping("/delete/{senderId}")
    public String deleteSender(@PathVariable Long senderId) {
        senderService.deleteSender(senderId);
        return "Sender supprimé avec succès, ID: " + senderId;
    }
}
