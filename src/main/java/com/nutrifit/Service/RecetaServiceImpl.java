package com.nutrifit.Service;

import com.nutrifit.Clases.Receta;
import com.nutrifit.Dao.IReceta;
import com.nutrifit.Repository.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class RecetaServiceImpl implements IReceta {

    @Autowired
    private RecetaRepository recetaRepository;

    @Override
    public Optional<Receta> findById(Long id_receta) {
        return recetaRepository.findById(id_receta);
    }

    @Override
    public Map<String, Object> obtenerDetalleReceta(Long id_receta) {
        return recetaRepository.obtenerDetalleReceta(id_receta);
    }
    
    @Override
    public Optional<Receta> findByNombre(String nombrembrePlato) {
        return recetaRepository.findByNombre(nombrembrePlato);
    }
    
    @Override
    public Map<String, Object> obtenerDetalleRecetaPorNombre(String nombrePlato) {
        return recetaRepository.obtenerDetalleRecetaPorNombre(nombrePlato);
    }
}
