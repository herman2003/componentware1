package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.entity.User;
import com.componentware.wasserdatabase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializationUserService implements CommandLineRunner {
    @Autowired
    private final UserRepository userRepository;

    public DataInitializationUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String defaultName = "User";
        String defaultEmail = "admin@example.com";
        String defaultDescription = "Default administrator account";
        String defaultPassword = "admin123"; // Hachez ce mot de passe dans un environnement de production

        // Vérifiez si un utilisateur existe déjà en fonction de l'email
        if (userRepository.findByEmail(defaultEmail) == null) {
            User defaultUser = new User(defaultName,defaultEmail,defaultDescription,defaultPassword);
            userRepository.save(defaultUser);

            System.out.println("Utilisateur par défaut créé : " + defaultEmail);
        } else {
            System.out.println("Utilisateur par défaut déjà existant.");
        }
    }
}
