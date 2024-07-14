package com.nutrifit.Service;

import com.nutrifit.Clases.PlandeComida;
import com.nutrifit.Clases.Receta;
import com.nutrifit.Clases.Usuario;
import com.nutrifit.Dao.IPlandecomida;
import com.nutrifit.Repository.PlandecomidaRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlandecomidaServiceImpl implements IPlandecomida {

    @Autowired
    private PlandecomidaRepository planRepository;
    
    @Override
    public boolean save(PlandeComida plan, long usuario) {
        return planRepository.save(plan, usuario);
    }

    @Override
    public double obtenerIMC(long idUsuario) {
        return planRepository.obtenerIMC(idUsuario);
    }

    @Override
    public double capturarPresupuesto(long idUsuario) {
        return planRepository.capturarPresupuesto(idUsuario);
    }

    @Override
    public String obtenerRecomendacionesIMC(long idUsuario) {
        double imc = obtenerIMC(idUsuario);
        List<String> recomendaciones = planRepository.obtenerRecomendacionesIMC(imc);

        // Convertir la lista de recomendaciones a un formato de cadena deseado
        String recomendacionesString = recomendaciones.stream()
                .collect(Collectors.joining("\n"));  // O utiliza el separador que prefieras

        return recomendacionesString;
    }

    @Override
    public List<String> obtenerRecomendacionesNombre(long idUsuario, String nombrePlato) {
        double imc = obtenerIMC(idUsuario);
        List<String> recomendaciones = planRepository.obtenerRecomendacionesNombre(imc, nombrePlato);

        return recomendaciones;
    }

    @Override
    public boolean recetaplan(long receta, long user, String dias) {
        return planRepository.recetaplan(receta, user, dias);
    }
}
