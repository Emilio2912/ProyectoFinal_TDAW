package com.upiiz.ProyectoFinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping
    public String mostrarFormularioRegistro() {
        return "registro";
    }

   @PostMapping
public String guardarAspirante(
    @RequestParam String nombre,
    @RequestParam String apellido,
    @RequestParam String email,
    @RequestParam(required = false) String telefono,
    @RequestParam(required = false) String carrera
){
        String sql = "INSERT INTO aspirantes (nombre, apellido, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, nombre, apellido, email);

        // Enviar correo al admin
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("jesusad250@gmail.com"); 
        message.setSubject("Nuevo registro de aspirante");
        message.setText("Se ha registrado un nuevo aspirante:\nNombre: " + nombre + " " + apellido + "\nEmail: " + email);
        mailSender.send(message);

        return "redirect:/registro?exito";
    }

    @PostMapping("/verificar-email")
    @ResponseBody
    public Map<String, Boolean> verificarEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean existe = false;
        if (email != null && !email.isEmpty()) {
            existe = existeEmail(email);
        }
        return Collections.singletonMap("existe", existe);
    }

    private boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) FROM aspirantes WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}
