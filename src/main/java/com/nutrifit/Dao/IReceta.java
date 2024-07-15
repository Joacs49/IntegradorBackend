package com.nutrifit.Dao;

import com.nutrifit.Clases.Receta;
import java.util.Map;
import java.util.Optional;

public interface IReceta {

    public Optional<Receta> findById(Long id_receta);

    public Map<String, Object> obtenerDetalleReceta(Long id_receta);

    public Optional<Receta> findByNombre(String nombrembrePlato);

    public Map<String, Object> obtenerDetalleRecetaPorNombre(String nombrePlato);
}
