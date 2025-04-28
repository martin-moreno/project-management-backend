package co.spa.projectmanagement.integrators.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "fiscal_responsibilities")
@Getter
@Setter
@ToString
public class FiscalResponsibility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "integrator_id", nullable = false)
    @JsonBackReference
    private Integrator integrator;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String type; // Example: Major taxpayer, Self-employed, etc.
}