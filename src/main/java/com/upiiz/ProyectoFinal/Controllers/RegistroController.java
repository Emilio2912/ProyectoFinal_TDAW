package com.upiiz.ProyectoFinal.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistroController {
    @GetMapping("/")
    public String home() {
        return "registro";
    }
}
