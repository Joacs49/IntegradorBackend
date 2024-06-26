package com.nutrifit.Web;

import com.nutrifit.Clases.Usuario;
import com.nutrifit.Service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Usuario usuario, Errors errors) {
        if (errors.hasErrors()) {
            throw new IllegalArgumentException("Datos de usuario no válidos");
        }
        Usuario existe = usuarioService.existeCorreo(usuario.getCorreo());
        
        if (existe != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El correo electrónico ya está en uso");
        }

        usuarioService.guardar(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok().body("Sesión cerrada correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Usuario usuario) {
        Usuario usuarioAutenticado = usuarioService.autenticarUsuario(usuario.getCorreo(), usuario.getClave());

        if (usuarioAutenticado != null) {
            return ResponseEntity.ok().body("Autenticación exitosa para el usuario: " + usuarioAutenticado.getCorreo());
        } else {

            Usuario usuarioExistente = usuarioService.existeCorreo(usuario.getCorreo());
            if (usuarioExistente != null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Correo no encontrado");
            }
        }
    }

    @GetMapping("/usuario/{correo}")
    public ResponseEntity<?> getUsuarioPorCorreo(@PathVariable String correo, @RequestParam String clave) {
        Usuario usuario = usuarioService.autenticarUsuario(correo, clave);

        if (usuario != null) {
            return ResponseEntity.ok().body(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PutMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Usuario idUsuario, @RequestBody Usuario usuarioActualizado) {
        Usuario usuarioEncontrado = usuarioService.buscar(idUsuario);

        if (usuarioEncontrado != null) {

            usuarioEncontrado.setNombre(usuarioActualizado.getNombre());
            usuarioEncontrado.setCorreo(usuarioActualizado.getCorreo());
            usuarioEncontrado.setGenero(usuarioActualizado.getGenero());
            usuarioEncontrado.setAltura(usuarioActualizado.getAltura());
            usuarioEncontrado.setEdad(usuarioActualizado.getEdad());
            usuarioEncontrado.setPeso(usuarioActualizado.getPeso());

            usuarioService.guardar(usuarioEncontrado);

            return ResponseEntity.ok().body("Datos actualizados correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

}
