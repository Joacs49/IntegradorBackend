
package com.nutrifit.Web;

import com.nutrifit.Clases.Receta;
import com.nutrifit.Dao.IReceta;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recetas")
public class ControladorReceta {
    
    @Autowired
    private IReceta recetaService;

    @GetMapping("/{id}")
    public ResponseEntity<Receta> obtenerRecetaPorId(@PathVariable Long id) {
        Optional<Receta> receta = recetaService.findById(id);
        return receta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Map<String, Object>> obtenerDetalleReceta(@PathVariable Long id) {
        Map<String, Object> detalle = recetaService.obtenerDetalleReceta(id);
        if (detalle != null) {
            return ResponseEntity.ok(detalle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}