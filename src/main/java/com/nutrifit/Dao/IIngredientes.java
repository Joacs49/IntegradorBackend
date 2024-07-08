
package com.nutrifit.Dao;


import com.nutrifit.Clases.Ingredientes;
import java.util.Optional;

public interface IIngredientes {
    
    public Optional<Ingredientes> findById(Long id_ingrediente);
    
}
