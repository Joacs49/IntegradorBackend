package com.nutrifit.Repository;

import com.nutrifit.Clases.Ingredientes;
import com.nutrifit.Clases.Receta;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RecetaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<Receta> findById(Long id_receta) {
        try {
            String sql = "SELECT * FROM RECETA WHERE id_receta = ?";
            Object[] args = {id_receta};
            int[] argTypes = {Types.BIGINT};

            Receta receta = jdbcTemplate.queryForObject(sql, args, argTypes, (rs, rowNum) -> {
                Receta r = new Receta();
                r.setId_receta(rs.getLong("ID_RECETA"));
                r.setNombreReceta(rs.getString("NOMBRE_RECETA"));
                return r;
            });
            return Optional.ofNullable(receta);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Map<String, Object> obtenerDetalleReceta(Long id_receta) {
        Optional<Receta> recetaOptional = findById(id_receta);
        if (recetaOptional.isEmpty()) {
            return null; // Si no se encuentra la receta, retornamos null
        }

        Receta receta = recetaOptional.get();

        // Variables para calcular el total de precios
        double totalPrecio = 0;

        String sqlIngredientes = "SELECT i.* FROM INGREDIENTES i "
                + "JOIN RECETAINGREDIENTE ri ON i.id_ingrediente = ri.id_ingrediente "
                + "WHERE ri.id_receta = ?";

        Object[] args = {id_receta};
        int[] argTypes = {Types.BIGINT};

        List<Map<String, Object>> ingredientes = jdbcTemplate.queryForList(sqlIngredientes, args, argTypes);

        List<Map<String, Object>> detalleIngredientes = new ArrayList<>();
        for (Map<String, Object> ingrediente : ingredientes) {
            // Extraer los datos necesarios del ingrediente
            String nombreIngrediente = (String) ingrediente.get("nombre_ingrediente");
            double precioUnitario = (double) ingrediente.get("precio");
            double totalIngrediente = precioUnitario; // Suponiendo que el total de cada ingrediente es el precio unitario

            // Calcular el total de precios
            totalPrecio += totalIngrediente;

            // Crear el detalle del ingrediente
            Map<String, Object> detalleIngrediente = new HashMap<>();
            detalleIngrediente.put("nombreIngrediente", nombreIngrediente);
            detalleIngrediente.put("precioUnitario", precioUnitario);
            detalleIngrediente.put("totalIngrediente", totalIngrediente);

            // Agregar detalle de ingrediente a la lista
            detalleIngredientes.add(detalleIngrediente);
        }

        // Crear el detalle completo de la receta
        Map<String, Object> detalleReceta = new LinkedHashMap<>();
        detalleReceta.put("nombreReceta", receta.getNombreReceta());
        detalleReceta.put("ingredientes", detalleIngredientes);
        detalleReceta.put("totalPrecio", totalPrecio);

        return detalleReceta;
    }

    public Optional<Receta> findByNombre(String nombrePlato) {
        try {
            String sql = "SELECT * FROM RECETA WHERE NOMBRE_RECETA = ?";
            Object[] args = {nombrePlato};
            int[] argTypes = {Types.VARCHAR};

            Receta receta = jdbcTemplate.queryForObject(sql, args, argTypes, (rs, rowNum) -> {
                Receta r = new Receta();
                r.setId_receta(rs.getLong("ID_RECETA"));
                r.setNombreReceta(rs.getString("NOMBRE_RECETA"));
                return r;
            });

            return Optional.ofNullable(receta);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Map<String, Object> obtenerDetalleRecetaPorNombre(String nombrePlato) {
        Optional<Receta> recetaOptional = findByNombre(nombrePlato);
        if (recetaOptional.isEmpty()) {
            return null; // Si no se encuentra la receta, retornamos null
        }

        Receta receta = recetaOptional.get();

        String sqlIngredientes = "SELECT i.nombre_ingrediente FROM INGREDIENTES i "
                + "JOIN RECETAINGREDIENTE ri ON i.id_ingrediente = ri.id_ingrediente "
                + "WHERE ri.id_receta = ?";

        Object[] args = {receta.getId_receta()};
        int[] argTypes = {Types.BIGINT};

        List<Map<String, Object>> ingredientes = jdbcTemplate.queryForList(sqlIngredientes, args, argTypes);

        List<String> nombresIngredientes = new ArrayList<>();
        for (Map<String, Object> ingrediente : ingredientes) {
            String nombreIngrediente = (String) ingrediente.get("nombre_ingrediente");
            nombresIngredientes.add(nombreIngrediente);
        }

        // Crear el detalle completo de la receta con solo el nombre y los nombres de los ingredientes
        Map<String, Object> detalleReceta = new LinkedHashMap<>();
        detalleReceta.put("nombreReceta", receta.getNombreReceta());
        detalleReceta.put("ingredientes", nombresIngredientes);

        return detalleReceta;
    }

}
