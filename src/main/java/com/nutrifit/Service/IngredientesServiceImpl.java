/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nutrifit.Service;

import com.nutrifit.Clases.Ingredientes;
import com.nutrifit.Dao.IIngredientes;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class IngredientesServiceImpl implements IIngredientes{
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
