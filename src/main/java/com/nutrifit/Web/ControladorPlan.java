package com.nutrifit.Web;

import com.nutrifit.Clases.PlandeComida;
import com.nutrifit.Clases.Receta;
import com.nutrifit.Clases.Usuario;
import com.nutrifit.Dao.IPlandecomida;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plandecomida")
public class ControladorPlan {

    @Autowired
    private IPlandecomida plandecomidaService;

    @PostMapping("/register")
    public ResponseEntity<String> savePlandeComida(@RequestBody PlandeComida plan,
            @RequestParam long userId) {
        try {
            boolean planGuardado = plandecomidaService.save(plan, userId);

            if (planGuardado) {
                return ResponseEntity.ok("Plan de comida guardado correctamente.");
            } else {
                return ResponseEntity.badRequest().body("Error al guardar el plan de comida.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al procesar la solicitud.");
        }
    }

@PostMapping("/recetaplan")
public ResponseEntity<String> saveRecetaPlan(
        @RequestParam Long userId,
        @RequestBody Map<String, Object> requestBody) {
    try {
        List<Long> recetas = ((List<?>) requestBody.get("recetas")).stream()
                .map(o -> Long.parseLong(String.valueOf(o)))
                .collect(Collectors.toList());
        List<String> dias = (List<String>) requestBody.get("dias");

        // Verificar que recetas y días no sean null
        if (recetas == null || dias == null) {
            return ResponseEntity.badRequest().body("Los parámetros 'recetas' y 'dias' son requeridos.");
        }

        // Procesar cada receta y día
        for (Long receta : recetas) {
            for (String dia : dias) {
                boolean planGuardado = plandecomidaService.recetaplan(receta, userId, dia);

                if (!planGuardado) {
                    return ResponseEntity.badRequest().body("Error al guardar el plan de comida.");
                }
            }
        }

        return ResponseEntity.ok("Planes de comida guardados correctamente.");
    } catch (NumberFormatException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body("Error de formato en los datos.");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno al procesar la solicitud.");
    }
}






    @GetMapping("/imc/{idUsuario}")
    public double obtenerIMC(@PathVariable long idUsuario) {
        return plandecomidaService.obtenerIMC(idUsuario);
    }

    @GetMapping("/presupuesto/{idUsuario}")
    public double capturarPresupuesto(@PathVariable long idUsuario) {
        return plandecomidaService.capturarPresupuesto(idUsuario);
    }

    @GetMapping("/recomendaciones/{idUsuario}")
    public String obtenerRecomendacionesIMC(@PathVariable long idUsuario) {
        return plandecomidaService.obtenerRecomendacionesIMC(idUsuario);
    }

    @GetMapping("/busqueda/{idUsuario}/{nombrePlato}")
    public List<String> obtenerRecomendacionesPorNombre(@PathVariable long idUsuario, @PathVariable String nombrePlato) {
        List<String> recomendaciones = plandecomidaService.obtenerRecomendacionesNombre(idUsuario, nombrePlato);

        return recomendaciones;
    }
}
