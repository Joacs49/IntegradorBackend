
package com.nutrifit.Repository;

import com.nutrifit.Clases.Ingredientes;
import com.nutrifit.Service.IngredienteRowMapper;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IngredientesRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<Ingredientes> findById(Long id_ingrediente) {
        try {
            String sql = "SELECT * FROM INGREDIENTES WHERE ID_INGREDIENTE = ?";
            Object[] args = {id_ingrediente};
            int[] argTypes = {java.sql.Types.BIGINT};

            Ingredientes ingrediente = jdbcTemplate.queryForObject(sql, args, argTypes, new IngredienteRowMapper());
            return Optional.ofNullable(ingrediente);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
