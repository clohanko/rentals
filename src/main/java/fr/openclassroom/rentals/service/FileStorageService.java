package fr.openclassroom.rentals.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path uploadDir = Paths.get("uploads");

    public String store(MultipartFile file) {
        try {
            if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path target = uploadDir.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            // retourne l’URL d’accès, adapte le hostname/port si besoin
            return "http://localhost:3001/uploads/" + filename;
        } catch (Exception e) {
            throw new RuntimeException("Échec du stockage du fichier", e);
        }
    }
}
