package com.nutrifit.Service;

import com.nutrifit.Clases.Ingredientes;
import com.nutrifit.Dao.IIngredientes;
import com.nutrifit.Repository.IngredientesRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IngredientesServiceImpl implements IIngredientes{
    
    @Autowired
    private IngredientesRepository ingredientesRepository;

    @Override
    public Optional<Ingredientes> findById(Long id_ingrediente) {
        return ingredientesRepository.findById(id_ingrediente);
    }
}
