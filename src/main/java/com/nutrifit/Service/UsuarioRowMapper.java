package com.nutrifit.Service;

import com.nutrifit.Clases.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


public class UsuarioRowMapper implements RowMapper<Usuario>{

    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getLong("ID_USUARIO"));
        usuario.setNombre(rs.getString("NOMBRE"));
        usuario.setCorreo(rs.getString("CORREO"));
        usuario.setGenero(rs.getString("GENERO"));
        usuario.setAltura(rs.getDouble("ALTURA"));
        usuario.setEdad(rs.getInt("EDAD"));
        usuario.setPeso(rs.getDouble("PESO"));
        usuario.setClave(rs.getString("CLAVE"));
        return usuario;
    }
    
}
