package co.spa.projectmanagement.security.service;

import co.spa.projectmanagement.security.model.User;
import co.spa.projectmanagement.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;  // Asumiendo que tienes un repositorio para la entidad UserData

    public User getUserDataByUsername(String username) {
        // Aquí buscamos al usuario en la base de datos usando el nombre de usuario
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}