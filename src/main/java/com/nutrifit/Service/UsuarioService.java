package com.nutrifit.Service;

import com.nutrifit.Clases.Usuario;
import java.util.List;

public interface UsuarioService {
    
    public List<Usuario> listar();
    
    public void guardar(Usuario user);
    
    public void eliminar(Usuario user);
    
    public Usuario buscar(Usuario user);
    
    public Usuario autenticarUsuario(String correo, String clave);
    
    public Usuario existeCorreo(String correo);
}
