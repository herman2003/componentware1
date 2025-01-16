package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.entity.Auftrag;
import com.componentware.wasserdatabase.repository.AuftragRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuftragsService {

    @Autowired
    private AuftragRepository auftragRepository;

    // Récupérer tous les Aufträge d'un utilisateur spécifique
    public List<Auftrag> getAuftragByUserId(Long userId) {
        return auftragRepository.findAuftragByUser_Id(userId);
    }
    public List<Auftrag> getAuftragByType(String type) {
        return auftragRepository.findAuftragByType(type);
    }
    // Méthode pour traiter un Auftrag (comme dans votre code initial)
    public void auftragsberarbeiten(Auftrag auftrag) {
        // Code pour traiter un Auftrag
    }
}
