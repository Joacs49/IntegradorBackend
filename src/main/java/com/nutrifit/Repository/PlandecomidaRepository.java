package com.nutrifit.Repository;

import com.nutrifit.Clases.Usuario;
import com.nutrifit.Dao.IUsuario;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlandecomidaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IUsuario usuarioRepository;

    public double obtenerIMC(long idUsuario) {
        try {
            Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
            if (usuario != null) {
                double peso = usuario.getPeso();
                double altura = usuario.getAltura();

                if (altura > 0) {
                    double imc = peso / (altura * altura);
                    return imc;
                } else {
                    System.out.println("Altura no válida: " + altura);
                }
            } else {
                System.out.println("No se encontró un usuario con ID: " + idUsuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public double capturarPresupuesto(long idUsuario) {
        try {
            String sql = "SELECT monto FROM Gastos WHERE id_usuario = ?";
            Object[] args = {idUsuario};
            int[] argTypes = {java.sql.Types.BIGINT};

            Double monto = jdbcTemplate.queryForObject(sql, args, argTypes, Double.class);
            return monto != null ? monto : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public List<String> obtenerRecomendacionesIMC(double imc) {
        try {
            String sql = "SELECT obtenerRecomendaciones(?) AS recomendaciones";
            Object[] args = {imc};
            int[] argTypes = {java.sql.Types.DOUBLE};

            String recomendacionesTexto = jdbcTemplate.queryForObject(sql, args, argTypes, String.class);

            // Separar las recomendaciones por "; "
            String[] recomendacionesArray = recomendacionesTexto.split("; ");

            List<String> recomendacionesList = new ArrayList<>();

            // Agregar cada recomendación al arreglo resultante
            for (String recomendacion : recomendacionesArray) {
                recomendacionesList.add(recomendacion.trim());
            }

            return recomendacionesList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<String> obtenerRecomendacionesNombre(double imc, String nombrePlato) {
        try {
            // Obtener todas las recomendaciones por IMC
            String sql = "SELECT obtenerRecomendaciones(?) AS recomendaciones";
            Object[] args = {imc};
            int[] argTypes = {java.sql.Types.DOUBLE};
            String recomendacionesTexto = jdbcTemplate.queryForObject(sql, args, argTypes, String.class);

            // Separar las recomendaciones por "; "
            String[] recomendacionesArray = recomendacionesTexto.split("; ");
            List<String> recomendacionesList = Arrays.asList(recomendacionesArray);

            // Filtrar las recomendaciones por nombre de plato
            List<String> recomendacionesFiltradas = recomendacionesList.stream()
                    .filter(recomendacion -> recomendacion.contains(nombrePlato))
                    .collect(Collectors.toList());

            return recomendacionesFiltradas;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
