package co.spa.projectmanagement.integrators.controller;

import co.spa.projectmanagement.exception.ApiError;
import co.spa.projectmanagement.integrators.dto.IntegratorUpdateDTO;
import co.spa.projectmanagement.integrators.model.Integrator;
import co.spa.projectmanagement.integrators.service.IntegratorService;

import co.spa.projectmanagement.integrators.validators.ValidIntegratorUpdate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/integrators")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class IntegratorController {

    private static final Logger logger = LoggerFactory.getLogger(IntegratorController.class);
    private final IntegratorService integratorService;

    // Crear un nuevo integrador (solo accesible para usuarios con rol adecuado)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createIntegrator(@RequestBody Integrator integrator) {
        try {
            Integrator createdIntegrator = integratorService.createIntegrator(integrator);
            return new ResponseEntity<>(createdIntegrator, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Si el integrador ya existe, devolvemos un error 400 con el mensaje
            ApiError apiError = new ApiError("BAD_REQUEST", e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // En caso de otros errores, se retorna un error 500 con un mensaje genérico
            ApiError apiError = new ApiError("INTERNAL_SERVER_ERROR", "Ocurrió un error interno");
            return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todos los integradores (solo accesible para usuarios con rol adecuado)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")  // Solo usuarios con el rol 'ADMIN' pueden acceder
    public ResponseEntity<List<Integrator>> getAllIntegrators() {
        logger.info("Fetching all integrators");
        List<Integrator> integrators = integratorService.getAllIntegrators();
        logger.info("Found {} integrators", integrators.size());
        return new ResponseEntity<>(integrators, HttpStatus.OK);
    }

    // Obtener un integrador por su ID (solo accesible para usuarios con rol adecuado)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // Solo usuarios con el rol 'ADMIN' pueden acceder
    public ResponseEntity<Integrator> getIntegratorById(@PathVariable Long id) {
        logger.info("Fetching integrator with ID: {}", id);
        Optional<Integrator> integrator = integratorService.getIntegratorById(id);
        return integrator.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Integrator with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Actualizar un integrador existente (solo accesible para usuarios con rol adecuado)
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // Solo usuarios con el rol 'ADMIN' pueden acceder
    public ResponseEntity<Integrator> updateIntegrator(@PathVariable Long id,
                                                       @RequestBody  @Valid IntegratorUpdateDTO integratorUpdateDTO) {
        try {
            Integrator updatedIntegrator = integratorService.updateIntegrator(id, integratorUpdateDTO);
            return ResponseEntity.ok(updatedIntegrator);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    // Eliminar un integrador por su ID (solo accesible para usuarios con rol adecuado)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // Solo usuarios con el rol 'ADMIN' pueden acceder
    public ResponseEntity<Void> deleteIntegrator(@PathVariable Long id) {
        logger.info("Deleting integrator with ID: {}", id);
        integratorService.deleteIntegrator(id);
        logger.info("Integrator with ID: {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}