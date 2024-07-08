package com.nutrifit.Web;

import com.nutrifit.Dao.IPlandecomida;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/plandecomida")
public class ControladorPlan {

    @Autowired
    private IPlandecomida plandecomidaService;

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