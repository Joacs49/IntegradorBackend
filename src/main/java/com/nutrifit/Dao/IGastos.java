package com.nutrifit.Dao;

import com.nutrifit.Clases.Gastos;
import com.nutrifit.Clases.Usuario;
import java.util.List;

import java.util.Optional;

public interface IGastos {

    public Optional<Usuario> findById(Long id);
    public List<Gastos> findGastosByUsuarioId(Long idUsuario);
}
