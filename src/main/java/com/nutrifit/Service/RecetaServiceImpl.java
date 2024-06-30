package com.nutrifit.Service;

import com.nutrifit.Clases.Ingredientes;
import com.nutrifit.Clases.Receta;
import com.nutrifit.Dao.IReceta;
import java.text.DecimalFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Service
public class RecetaServiceImpl implements IReceta {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Receta> findById(Long id_receta) {
        try {
            String sql = "SELECT * FROM RECETA WHERE id_receta = ?";
            Receta receta = jdbcTemplate.queryForObject(sql, new Object[]{id_receta}, (rs, rowNum) -> {
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

    @Override
    public Map<String, Object> obtenerDetalleReceta(Long id_receta) {
        Optional<Receta> recetas = findById(id_receta);
        if (!recetas.isPresent()) {
            return null;
        }

        Receta receta = recetas.get();
        double totalCalorias = 0;
        double totalProteinas = 0;
        double totalCarbohidratos = 0;
        double totalGrasas = 0;
        double totalPrecio=0;

        String sqlIngredientes = "SELECT i.* FROM INGREDIENTES i " +
                "JOIN RECETAINGREDIENTE ri ON i.id_ingrediente = ri.id_ingrediente " +
                "WHERE ri.id_receta = ?";

        List<Ingredientes> ingredientes = jdbcTemplate.query(sqlIngredientes, new Object[]{id_receta}, (rs, rowNum) -> {
            Ingredientes ingrediente = new Ingredientes();
            ingrediente.setId_ingrediente(rs.getLong("id_ingrediente"));
            ingrediente.setPrecio(rs.getInt("precio"));
            ingrediente.setNombre(rs.getString("nombre_ingrediente"));
            ingrediente.setCalorias(rs.getDouble("calorias"));
            ingrediente.setProteinas(rs.getDouble("proteinas"));
            ingrediente.setCarbohidratos(rs.getDouble("carbohidratos"));
            ingrediente.setGrasas(rs.getDouble("grasas"));
            return ingrediente;
        });

        for (Ingredientes ingrediente : ingredientes) {
            totalCalorias += ingrediente.getCalorias();
            totalProteinas += ingrediente.getProteinas();
            totalCarbohidratos += ingrediente.getCarbohidratos();
            totalGrasas += ingrediente.getGrasas();
            totalPrecio += ingrediente.getPrecio();
        }
        
        DecimalFormat df = new DecimalFormat("#.##");
        
        Map<String, Object> detalle = new LinkedHashMap<>();
        detalle.put("nombreReceta", receta.getNombreReceta());
        detalle.put("ingredientes", ingredientes);
        detalle.put("totalCalorias", Double.valueOf(df.format(totalCalorias)));
        detalle.put("totalProteinas", Double.valueOf(df.format(totalProteinas)));
        detalle.put("totalCarbohidratos", Double.valueOf(df.format(totalCarbohidratos)));
        detalle.put("totalGrasas", Double.valueOf(df.format(totalGrasas)));
        detalle.put("totalPrecio", Double.valueOf(df.format(totalPrecio)));

        return detalle;
    }
}
