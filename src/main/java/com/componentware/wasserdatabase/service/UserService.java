package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.entity.User;
import com.componentware.wasserdatabase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Méthode pour récupérer un utilisateur par email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);  // Assurez-vous que vous avez cette méthode dans votre UserRepository
    }
}
