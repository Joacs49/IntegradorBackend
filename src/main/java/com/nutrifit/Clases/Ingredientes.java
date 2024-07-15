package com.nutrifit.Clases;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;

@Entity
@Data
@Table(name = "INGREDIENTES")
public class Ingredientes implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_ingrediente;

    private String nombre;
    private double precio;
    private double calorias;
    private double proteinas;
    private double carbohidratos;
    private double grasas;

    @ManyToOne
    private Receta receta;
}
