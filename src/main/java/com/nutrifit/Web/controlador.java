package com.nutrifit.Web;

import com.nutrifit.Clases.Usuario;
import com.nutrifit.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")  
public class controlador {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public Usuario registrarUsuario(@Valid @RequestBody Usuario usuario, Errors errors) {
        if (errors.hasErrors()) {
            throw new IllegalArgumentException("Datos de usuario no válidos");
        }

        usuarioService.guardar(usuario);

        return usuario;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Usuario usuario) {
        Usuario usuarioAutenticado = usuarioService.autenticarUsuario(usuario.getCorreo(), usuario.getClave());

        if (usuarioAutenticado != null) {
            return ResponseEntity.ok().body("Autenticación exitosa para el usuario: " + usuarioAutenticado.getCorreo());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

}
