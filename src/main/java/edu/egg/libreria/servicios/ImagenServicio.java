package edu.egg.libreria.servicios;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImagenServicio {

    private static final String DIRECTORIO = "src/main/resources/static/subidas";

    public String copy(MultipartFile foto) {
        try {
            String nombreFoto = foto.getOriginalFilename();
            Path rutaFoto = Paths.get(DIRECTORIO, nombreFoto).toAbsolutePath();
            Files.copy(foto.getInputStream(), rutaFoto, StandardCopyOption.REPLACE_EXISTING);
            return nombreFoto;
        } catch (IOException e) {
            throw new IllegalArgumentException("Error guardando imagen");
        }
    }
}
