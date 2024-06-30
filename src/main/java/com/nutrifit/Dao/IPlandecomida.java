/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nutrifit.Dao;

import java.util.Map;


public interface IPlandecomida {
    public double obtenerIMC(long idUsuario);
    public double capturarPresupuesto(long idUsuario);
    public String obtenerRecomendacionesIMC(long idUsuario);
}

