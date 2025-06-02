package com.upiiz.ProyectoFinal.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarrerasController {
    @GetMapping("/carreras")
    public String carreras() {
        return "carreras";
    }
}
