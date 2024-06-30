package com.nutrifit.Web;

import com.nutrifit.Clases.Ingredientes;
import com.nutrifit.Dao.IIngredientes;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/ingredientes")
public class ControladorIngredientes {

    @Autowired
    private IIngredientes ingredienteRepository;
    
    @GetMapping("/{id}")
    public Optional<Ingredientes> findById(@PathVariable Long id) {
        return ingredienteRepository.findById(id);
    }
}
