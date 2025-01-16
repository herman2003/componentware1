package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.entity.User;
import com.componentware.wasserdatabase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Méthode pour récupérer un utilisateur par email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);  // Assurez-vous que vous avez cette méthode dans votre UserRepository
    }

    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new RuntimeException("Keine Benutzer gefunden.");
        }
        return users;
    }
    public User addUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Ein Benutzer mit dieser E-Mail existiert bereits.");
        }
        return userRepository.save(user);
    }
}
