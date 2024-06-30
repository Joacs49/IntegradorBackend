/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nutrifit.Service;

import com.nutrifit.Clases.Ingredientes;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Daniel
 */
public class IngredienteRowMapper implements RowMapper<Ingredientes> {
    @Override
    public Ingredientes mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ingredientes ingrediente = new Ingredientes();
        ingrediente.setId_ingrediente(rs.getLong("ID_INGREDIENTE"));
        ingrediente.setNombre(rs.getString("NOMBRE_INGREDIENTE"));
        ingrediente.setPrecio(rs.getDouble("PRECIO"));
        ingrediente.setCalorias(rs.getDouble("CALORIAS"));
        ingrediente.setProteinas(rs.getDouble("PROTEINAS"));
        ingrediente.setCarbohidratos(rs.getDouble("CARBOHIDRATOS"));
        ingrediente.setGrasas(rs.getDouble("GRASAS"));
        return ingrediente;
    }
}
