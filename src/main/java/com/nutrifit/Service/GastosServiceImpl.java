
package com.nutrifit.Service;

import com.nutrifit.Clases.Usuario;
import com.nutrifit.Dao.IGastos;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class GastosServiceImpl implements IGastos {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Usuario> findById(Long id) {
        try {
            String sql = "CALL ENCONTRAR_USUARIO(?)";
            Object[] args = {id};
            int[] argTypes = {java.sql.Types.BIGINT};

            Usuario usuario = jdbcTemplate.queryForObject(sql, args, argTypes, new UsuarioRowMapper());
            return Optional.ofNullable(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean ingresarGasto(Long idUsuario, double monto) {
        try {
            // Buscar el nombre del usuario por su ID
            Optional<Usuario> optionalUsuario = findById(idUsuario);
            if (optionalUsuario.isPresent()) {
                Usuario usuario = optionalUsuario.get();
                String nombreUsuario = usuario.getNombre(); // Obtener el nombre del usuario

                // Llamar al stored procedure pasando los parámetros correctos
                String sql = "CALL INGRESAR_GASTO(?,?,?)";
                jdbcTemplate.update(sql, nombreUsuario, monto, idUsuario);
                return true;
            } else {
                return false; // Usuario no encontrado
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

    
    
    
    

