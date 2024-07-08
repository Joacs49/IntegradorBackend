package com.nutrifit.Clases;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity
@Table(name = "GASTOS")
public class Gastos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_costo;

    @NotEmpty(message = "Usuario no puede estar vacío")
    private String usuario;

    @NotEmpty(message = "Fecha no puede estar vacía")
    private String fecha;

    @NotNull(message = "Monto no puede ser nulo")
    private double monto;
}
