package com.nutrifit.Service;

import com.nutrifit.Clases.Usuario;
import com.nutrifit.Dao.IUsuario;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuario {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(Usuario usuario) {
        String sql = "CALL CREAR_CUENTA(?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, usuario.getNombre(), usuario.getCorreo(), usuario.getGenero(),
                    usuario.getAltura(), usuario.getEdad(), usuario.getPeso(), usuario.getClave());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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
            return Optional.empty(); // Devolver Optional.empty() en caso de error
        }
    }

    @Override
    public void delete(Usuario usuario) {
        try {
            String sql = "CALL DELETE_USER(?)";
            jdbcTemplate.update(sql, usuario.getIdUsuario());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<Usuario> findAll() {
        try {
            String sql = "CALL ENCONTRAR_TODOS_USUARIOS()";
            List<Usuario> usuarios = jdbcTemplate.query(sql, new UsuarioRowMapper());
            return usuarios;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Usuario> findByCorreoAndClave(String correo, String clave) {
        String sql = "CALL ENCONTRAR_CORREO_AND_CLAVE(?,?)";
        Object[] params = {correo, clave};
        int[] types = {Types.VARCHAR, Types.VARCHAR};
        
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, types, new UsuarioRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Usuario> findByCorreo(String correo) {
        try {
            String sql = "CALL ENCONTRAR_CORREO(?)";
            Object[] args = {correo};
            int[] argTypes = {Types.VARCHAR};

            List<Usuario> usuarios = jdbcTemplate.query(sql, args, argTypes, new UsuarioRowMapper());

            // Verificar si la lista tiene exactamente un resultado
            if (usuarios.size() == 1) {
                return Optional.ofNullable(usuarios.get(0));
                
            } else if (usuarios.isEmpty()) {
                return Optional.empty(); 
                
            } else {
                throw new IllegalStateException("Se encontraron múltiples usuarios con el mismo correo");
            }
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty(); 
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar usuario por correo", e);
        }
    }

    @Override
    public void modificarUsuario(Usuario usuario1, Usuario usuario2) {
        String sql = "CALL MODIFICAR_USUARIO(?, ?, ?, ?, ?, ?, ?,?)";
        try {
            jdbcTemplate.update(sql, usuario1.getIdUsuario(), usuario2.getNombre(), usuario2.getCorreo(), usuario2.getGenero(),
                    usuario2.getAltura(), usuario2.getEdad(), usuario2.getPeso(), usuario2.getClave());
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al modificar usuario", e);
        }
    }
}
