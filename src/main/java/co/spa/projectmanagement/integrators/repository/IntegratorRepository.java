package co.spa.projectmanagement.integrators.repository;


import co.spa.projectmanagement.integrators.model.Integrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IntegratorRepository extends JpaRepository<Integrator, Long> {

    // Método para buscar un integrador por su nombre
    Optional<Integrator> findByName(String name);

    // Método para buscar un integrador por su email (si es necesario)
    Optional<Integrator> findByEmail(String email);

    boolean existsByIdNumber(String idNumber);

    // Método para verificar si el nombre ya existe
    boolean existsByName(String name);
}