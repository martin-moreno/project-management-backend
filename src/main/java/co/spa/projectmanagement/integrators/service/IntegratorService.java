package co.spa.projectmanagement.integrators.service;

import co.spa.projectmanagement.integrators.controller.IntegratorController;
import co.spa.projectmanagement.integrators.dto.IntegratorUpdateDTO;
import co.spa.projectmanagement.integrators.model.Contact;
import co.spa.projectmanagement.integrators.model.EndClient;
import co.spa.projectmanagement.integrators.model.FiscalResponsibility;
import co.spa.projectmanagement.integrators.model.Integrator;
import co.spa.projectmanagement.integrators.repository.IntegratorRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IntegratorService {

    private static final Logger logger = LoggerFactory.getLogger(IntegratorService.class);
    private final IntegratorRepository integratorRepository;

    // Crear un nuevo integrador
    public Integrator createIntegrator(Integrator integrator) {

        logger.info("Integrador creado: {}", integrator.toString());
        // Verificar si el integrador con el mismo idNumber ya existe
        if (integratorRepository.existsByIdNumber(integrator.getIdNumber())) {
            throw new IllegalArgumentException("El integrador con el ID especificado ya existe.");
        }

        // Verificar si el integrador con el mismo nombre ya existe (si aplica)
        if (integratorRepository.existsByName(integrator.getName())) {
            throw new IllegalArgumentException("El integrador con el nombre especificado ya existe.");
        }

        // Asegurarse de que cada Contact está asociado al Integrator
        if (integrator.getContacts() != null) {
            for (Contact contact : integrator.getContacts()) {
                contact.setIntegrator(integrator); // Asignar el integrator al contacto
            }
        }

        // Asegurarse de que cada FiscalResponsibility está asociado al Integrator
        if (integrator.getFiscalResponsibilities() != null) {
            for (FiscalResponsibility fiscalResponsibility : integrator.getFiscalResponsibilities()) {
                fiscalResponsibility.setIntegrator(integrator); // Asignar el integrator a la responsabilidad fiscal
            }
        }

        // Asegurarse de que cada EndClient está asociado al Integrator
        if (integrator.getEndClients() != null) {
            for (EndClient endClient : integrator.getEndClients()) {
                endClient.setIntegrator(integrator); // Asignar el integrator al cliente final
            }
        }
        return integratorRepository.save(integrator);
    }

    // Obtener todos los integradores
    public List<Integrator> getAllIntegrators() {
        List<Integrator> integrators = integratorRepository.findAll();
        // Si prefieres registrar cada uno de los campos de los integradores, puedes hacer algo así:
        for (Integrator integrator : integrators) {
            logger.info("Integrador -> id: {}, nombre: {}, correo: {}", integrator.getId(), integrator.getName(), integrator.getEmail());
        }

        return integratorRepository.findAll();
    }

    // Obtener un integrador por su ID
    public Optional<Integrator> getIntegratorById(Long id) {
        return integratorRepository.findById(id);
    }

    // Actualizar un integrador existente
    public Integrator updateIntegrator(Long id, IntegratorUpdateDTO integratorUpdateDTO) {
        Integrator integrator = integratorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Integrator not found"));

        if (integratorUpdateDTO.getName() != null) integrator.setName(integratorUpdateDTO.getName());
        if (integratorUpdateDTO.getEmail() != null) integrator.setEmail(integratorUpdateDTO.getEmail());
        if (integratorUpdateDTO.getPhone() != null) integrator.setPhone(integratorUpdateDTO.getPhone());
        if (integratorUpdateDTO.getAddress() != null) integrator.setAddress(integratorUpdateDTO.getAddress());

        return integratorRepository.save(integrator);
    }

    // Eliminar un integrador por su ID
    public void deleteIntegrator(Long id) {
        integratorRepository.deleteById(id);
    }
}