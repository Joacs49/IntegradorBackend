/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nutrifit.Web;

import com.nutrifit.Clases.Usuario;
import com.nutrifit.Dao.IGastos;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/gastos")
public class ControladorGastos {
    
    @Autowired
    private IGastos gastosService;
    
    
    @GetMapping("/usuario/{id}")
    public Optional<Usuario> findById(@PathVariable Long id) {
        return gastosService.findById(id);
    }

    @PostMapping("/ingresar")
    public boolean ingresarGasto(@RequestParam Long idUsuario,
                                 @RequestParam double monto) {
        return gastosService.ingresarGasto(idUsuario, monto);
    }
}
