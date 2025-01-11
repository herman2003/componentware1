package com.componentware.wasserdatabase.controller;

import com.componentware.wasserdatabase.entity.Sender;
import com.componentware.wasserdatabase.entity.User;
import com.componentware.wasserdatabase.repository.SenderRepository;
import com.componentware.wasserdatabase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sender")
public class SenderController {
    @Autowired
    private SenderRepository senderRepository; // Le repository pour les senders

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
        List<Sender> senders = senderRepository.findSendersByUser(user); // Méthode dans SenderRepository
        return senders;
    }
}
