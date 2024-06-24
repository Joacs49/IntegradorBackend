package com.nutrifit.Clases;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuario;

    @NotEmpty
    private String nombre;

    @NotEmpty(message = "Correo no puede estar vacío")
    @Email(message = "Correo debe ser válido")
    private String correo;

    @NotEmpty
    private String genero;

    @NotEmpty
    private double altura;

    @NotEmpty
    private int edad;

    @NotEmpty
    private double peso;

    @NotEmpty(message = "Correo no puede estar vacío")
    private String clave;

}
