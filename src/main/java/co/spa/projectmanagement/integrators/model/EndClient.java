package co.spa.projectmanagement.integrators.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "end_clients")
@Getter
@Setter
@ToString
public class EndClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String idType;

    @Column(nullable = false, unique = true)
    private String idNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    // Relation with the integrator
    @ManyToOne
    @JoinColumn(name = "integrator_id", nullable = false)
    @JsonBackReference
    private Integrator integrator;
}
