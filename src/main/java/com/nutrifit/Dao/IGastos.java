package com.nutrifit.Dao;

import com.nutrifit.Clases.Usuario;

import java.util.Optional;

public interface IGastos {

    public Optional<Usuario> findById(Long id);

    public boolean ingresarGasto(Long idUsuario, double monto);
}
