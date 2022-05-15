package edu.egg.libreria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServicio {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    private static final String SUBJECT = "Bienvenida";
    private static final String TEXT = "Bienvenido a nuestra página. Gracias por registrarse.";

    @Async
    public void enviarBienvenida(String to) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(to);
        mensaje.setFrom(from);
        mensaje.setSubject(SUBJECT);
        mensaje.setText(TEXT);
        sender.send(mensaje);
    }
}
