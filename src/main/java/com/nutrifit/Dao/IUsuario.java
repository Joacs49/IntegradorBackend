package com.nutrifit.Dao;

import com.nutrifit.Clases.Usuario;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface IUsuario extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByCorreoAndClave(String correo, String clave);
}
