
package com.nutrifit.Clases;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "PLANDECOMIDA")
public class PlandeComida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_plan;
    private String usuario;
    
    @OneToMany(mappedBy = "plancomida", cascade = CascadeType.ALL)
    private List<Receta> recetas;
}
