package com.nutrifit.Web;

import com.nutrifit.Clases.Usuario;
import com.nutrifit.Service.UsuarioServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class controlador {

    @Autowired
    private UsuarioServiceImpl usuarioService;
    

    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Usuario usuario, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body("Datos de usuario no válidos");
        }

        try {
            Optional<Usuario> existeOptional = usuarioService.findByCorreo(usuario.getCorreo());
            if (existeOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo electrónico ya está en uso");
            }

            boolean nuevoUsuario = usuarioService.save(usuario);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
            
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo electrónico ya está en uso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario");
        }
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

        Optional<Usuario> usuarioAutenticado = usuarioService.findByCorreoAndClave(usuario.getCorreo(), usuario.getClave());

        Usuario existe = usuarioAutenticado.orElse(null);

        if (existe != null) {
            return ResponseEntity.ok().body("Autenticación exitosa para el usuario: " + existe.getCorreo());
        } else {
            // Si no se encontró un usuario con correo y clave, verifica por correo
            Optional<Usuario> existeOptional = usuarioService.findByCorreo(usuario.getCorreo());
            Usuario existe2 = existeOptional.orElse(null);

            // Verificar si se encontró un usuario con el correo proporcionado
            if (existe2 != null) {
                // Retorna un error de contraseña incorrecta si el correo existe pero la clave es incorrecta
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
            } else {
                // Retorna un error de correo no encontrado si no existe ningún usuario con ese correo
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Correo no encontrado");
            }
        }
    }

    @GetMapping("/usuario/{correo}")
    public ResponseEntity<?> getUsuarioPorCorreo(@PathVariable String correo, @RequestParam String clave) {
        Optional<Usuario> usuarioAutenticado = usuarioService.findByCorreoAndClave(correo, clave);
        
        Usuario existe = usuarioAutenticado.orElse(null);

        if (existe != null) {
            return ResponseEntity.ok().body(existe);
            
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PutMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long idUsuario, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> usuarioEncontrado = usuarioService.findById(idUsuario);

        if (usuarioEncontrado.isPresent()) {
            Usuario existe = usuarioEncontrado.get();

            // Verificar si el correo actualizado ya existe para otro usuario
            Optional<Usuario> usuarioConCorreoExistente = usuarioService.findByCorreo(usuarioActualizado.getCorreo());

            // Si el correo electrónico existe y pertenece a otro usuario
            if (usuarioConCorreoExistente.isPresent() && !Objects.equals(usuarioConCorreoExistente.get().getIdUsuario(), idUsuario)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo electrónico ya está en uso");
            }

            existe.setNombre(usuarioActualizado.getNombre());
            existe.setCorreo(usuarioActualizado.getCorreo());
            existe.setGenero(usuarioActualizado.getGenero());
            existe.setAltura(usuarioActualizado.getAltura());
            existe.setEdad(usuarioActualizado.getEdad());
            existe.setPeso(usuarioActualizado.getPeso());
            existe.setClave(usuarioActualizado.getClave());

            try {
                usuarioService.modificarUsuario(existe, usuarioActualizado);
                
                return ResponseEntity.ok().body("Datos actualizados correctamente");
                
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el usuario");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

}
