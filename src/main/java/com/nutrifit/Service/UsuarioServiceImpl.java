package com.nutrifit.Service;

import com.nutrifit.Clases.Usuario;
import com.nutrifit.Dao.IUsuario;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    
    @Autowired
    private IUsuario userDao;

    @Override
    public List<Usuario> listar() {
        return (List<Usuario>) userDao.findAll();
    }

    @Override
    public void guardar(Usuario user) {
        userDao.save(user);
    }

    @Override
    public void eliminar(Usuario user) {
        userDao.delete(user);
    }

    @Override
    public Usuario buscar(Usuario user) {
        return userDao.findById(user.getIdUsuario()).orElse(null);
    }

    @Override
    public Usuario autenticarUsuario(String correo, String clave) {
        Optional<Usuario> optionalUsuario = userDao.findByCorreoAndClave(correo, clave);
        return optionalUsuario.orElse(null);
    }
}
