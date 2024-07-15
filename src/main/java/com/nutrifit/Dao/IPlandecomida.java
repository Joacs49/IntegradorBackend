package com.nutrifit.Dao;

import com.nutrifit.Clases.PlandeComida;
import com.nutrifit.Clases.Receta;
import com.nutrifit.Clases.Usuario;
import java.util.List;

public interface IPlandecomida {

    public double obtenerIMC(long idUsuario);

    public double capturarPresupuesto(long idUsuario);

    public String obtenerRecomendacionesIMC(long idUsuario);

    public List<String> obtenerRecomendacionesNombre(long idUsuario, String nombrePlato);

    public boolean save(PlandeComida plandeComida, long user);

    public boolean recetaplan(long receta, long user, String dias);
}
