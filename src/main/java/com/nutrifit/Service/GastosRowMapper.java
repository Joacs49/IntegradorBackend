package com.nutrifit.Service;

import com.nutrifit.Clases.Gastos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class GastosRowMapper implements RowMapper<Gastos>{

    @Override
    public Gastos mapRow(ResultSet rs, int rowNum) throws SQLException {
        Gastos gastos = new Gastos();
        gastos.setId_costo(rs.getInt("ID_COSTO"));
        gastos.setUsuario(rs.getString("USUARIO"));
        gastos.setFecha(rs.getString("FECHA"));
        gastos.setMonto(rs.getDouble("MONTO"));
        return gastos;
    }
    
}


