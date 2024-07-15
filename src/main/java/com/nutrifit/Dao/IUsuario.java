package com.nutrifit.Dao;

import com.nutrifit.Clases.Usuario;
import java.util.Optional;

public interface IUsuario {

    public boolean save(Usuario usuario);

    public Optional<Usuario> findById(Long id);

    public void delete(Usuario usuario);

    public Iterable<Usuario> findAll();

    public void modificarUsuario(Usuario usuario1, Usuario usuario2);

    public Optional<Usuario> findByCorreoAndClave(String correo, String clave);

    public Optional<Usuario> findByCorreo(String correo);
}
