/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nutrifit.Web;

import com.nutrifit.Dao.IPlandecomida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/plandecomida")
public class ControladorPlan {

    @Autowired
    private IPlandecomida plandecomidaService;

    @GetMapping("/imc/{idUsuario}")
    public double obtenerIMC(@PathVariable long idUsuario) {
        return plandecomidaService.obtenerIMC(idUsuario);
    }

    @GetMapping("/presupuesto/{idUsuario}")
    public double capturarPresupuesto(@PathVariable long idUsuario) {
        return plandecomidaService.capturarPresupuesto(idUsuario);
    }

    @GetMapping("/recomendaciones/{idUsuario}")
    public String obtenerRecomendacionesIMC(@PathVariable long idUsuario) {
        return plandecomidaService.obtenerRecomendacionesIMC(idUsuario);
    }
}