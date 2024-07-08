package com.nutrifit.Service;

import com.nutrifit.Clases.Usuario;
import com.nutrifit.Dao.IUsuario;
import com.nutrifit.Repository.UsuarioRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServiceImpl implements IUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    @Override
    public Iterable<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findByCorreoAndClave(String correo, String clave) {
        return usuarioRepository.findByCorreoAndClave(correo, clave);
    }

    @Override
    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @Override
    public void modificarUsuario(Usuario usuario1, Usuario usuario2) {
        usuarioRepository.modificarUsuario(usuario1, usuario2);
    }

}