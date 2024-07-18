package com.nutrifit.Service;

import com.nutrifit.Clases.Gastos;
import com.nutrifit.Clases.Usuario;
import com.nutrifit.Dao.IGastos;
import java.sql.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GastosRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    public List<Gastos> findGastosByUsuarioId(Long idUsuario) {
        String sql = "SELECT (USUARIO, FECHA, MONTO) FROM GASTOS WHERE ID_USUARIO = ?";
        
        Object[] args = {idUsuario};
        int[] argTypes = {Types.LONGVARBINARY};
        
        return jdbcTemplate.query(sql, args, argTypes, new GastosRowMapper());
    }
}
