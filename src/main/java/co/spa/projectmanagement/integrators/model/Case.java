package co.spa.projectmanagement.integrators.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cases")
@Getter
@Setter
@ToString
public class Case {

    // Getters y setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID del caso

    private String description;  // Descripción del caso, puedes agregar otros campos relevantes

    @ManyToOne
    @JoinColumn(name = "integrator_id")  // Clave foránea a la tabla 'integrator'
    @JsonBackReference
    private Integrator integrator;  // Relación con la clase Integrator

    // Otros campos que puedas necesitar (por ejemplo, estado, fecha de inicio, etc.)

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIntegrator(Integrator integrator) {
        this.integrator = integrator;
    }
}
