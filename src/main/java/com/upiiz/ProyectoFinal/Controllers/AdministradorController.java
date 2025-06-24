package com.upiiz.ProyectoFinal.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class    AdministradorController {

    // Página protegida: solo accesible si hay sesión iniciada
    @GetMapping("/administrador")
    public String administrador(HttpSession session) {
        // Verificar si existe la sesión del usuario
        if (session.getAttribute("usuario") == null) {
            // Si no hay sesión, redirigir al login
            return "redirect:/login";
        }
        return "administrador"; // Renderiza administrador.html
    }

    // Página de login (accesible públicamente)
    @GetMapping("/login")
    public String login() {
        return "administrador"; // Renderiza login.html
    }

    // Cierre de sesión
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); //  Cierra la sesión
        return "redirect:/administrador"; // Te redirige al login
    }

}

