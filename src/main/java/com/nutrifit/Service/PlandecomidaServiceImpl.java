/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nutrifit.Service;


import com.nutrifit.Clases.Usuario;

import com.nutrifit.Dao.IPlandecomida;
import com.nutrifit.Dao.IUsuario;
import java.text.DecimalFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PlandecomidaServiceImpl implements IPlandecomida {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IUsuario usuarioRepository; 
    


    public double obtenerIMC(long idUsuario) {
        try {
            Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null); // Asegúrate de manejar el caso de que no se encuentre el usuario
            if (usuario != null) {
                double peso = usuario.getPeso();
                double altura = usuario.getAltura();
                DecimalFormat df = new DecimalFormat("#.##");
                if (altura > 0) {
                    return Double.valueOf(df.format(peso / (altura * altura)));
                    }
                }
            } catch (EmptyResultDataAccessException e) {
                // Manejar el caso donde no se encuentra el usuario
                System.out.println("No se encontró un usuario con ID: " + idUsuario);
            } catch (Exception e) {
                // Manejar cualquier otra excepción que pueda surgir
                e.printStackTrace();
            }
            return 0.0; // En caso de datos incorrectos o inexistentes
        }
    

     @Override
    public double capturarPresupuesto(long idUsuario) {
        try {
            String sql = "SELECT monto FROM Gastos WHERE id_usuario = ?";
            Object[] args = {idUsuario};
            Double monto = jdbcTemplate.queryForObject(sql, args, Double.class);
            return monto != null ? monto : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0; 
        }
    }

    
    public String obtenerRecomendacionesIMC(long idUsuario) {
    try {
        // Obtener el IMC del usuario
        double imc = obtenerIMC(idUsuario);

        // Preparar la consulta SQL para llamar a la función SQL definida en la base de datos
        String sql = "SELECT obtenerRecomendaciones(?) AS recomendaciones";
        String recomendaciones = jdbcTemplate.queryForObject(sql, new Object[]{imc}, String.class);

        return recomendaciones;
    } catch (Exception e) {
        e.printStackTrace();
        return "Error al obtener recomendaciones";
    }
}



    
}
