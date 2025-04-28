package co.spa.projectmanagement.integrators.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "integrators")
@Getter
@Setter
@ToString
public class Integrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
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

    @Email
    @Column(nullable = false)
    private String email;

    // Relation with end clients
    @OneToMany(mappedBy = "integrator", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<EndClient> endClients = new ArrayList<>();

    // Billing data
    @Column(name = "billing_contact_name")
    private String billingContactName;

    @Column(name = "billing_phone")
    private String billingPhone;

    @Column(name = "billing_email")
    private String billingEmail;

    // Relation with fiscal responsibilities of the integrator
    @OneToMany(mappedBy = "integrator", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<FiscalResponsibility> fiscalResponsibilities = new ArrayList<>();

    // Relation with contacts (an integrator can have multiple contacts)
    @OneToMany(mappedBy = "integrator", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Contact> contacts = new ArrayList<>();

    @Column(name = "sales_rep")
    private String salesRep;

    @Column(name = "collector")
    private String collector;

    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "integrator", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Case> cases = new ArrayList<>();
}
