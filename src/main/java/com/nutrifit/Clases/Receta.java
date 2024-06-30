
package com.nutrifit.Clases;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "RECETA")
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_receta;
    private String nombreReceta;

    @OneToMany(mappedBy = "receta")
    private List<Ingredientes> ingredientes;
    
    @ManyToOne
    @JoinColumn(name = "id_plan")
    private PlandeComida plancomida;
}
