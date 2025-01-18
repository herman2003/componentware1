package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.entity.Nachricht;
import com.componentware.wasserdatabase.entity.Sender;
import com.componentware.wasserdatabase.repository.NachrichtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NachrichtService {

    @Autowired
    private NachrichtRepository nachrichtRepository; // Repository pour les Nachrichten

    // Récupérer toutes les Nachrichten liées à un Sender
    public List<Nachricht> findBySender(long sender_id) {
        return nachrichtRepository.findAllBySender_Id(sender_id); // Méthode dans le repository
    }
    public List<Nachricht> findAll() {
        return nachrichtRepository.findAll(); // Méthode dans le repository
    }

}
