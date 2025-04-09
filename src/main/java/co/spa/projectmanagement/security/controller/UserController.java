package co.spa.projectmanagement.security.controller;

import co.spa.projectmanagement.security.model.User;
import co.spa.projectmanagement.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;  // Inyección del servicio

    @GetMapping
    public User getUserData(@RequestHeader("Authorization") String token) {
        // Lógica para extraer el username del token y obtener los datos del usuario
        String username = extractUsernameFromToken(token);  // Necesitarás un método para extraer el nombre de usuario del token
        return userService.getUserDataByUsername(username);  // Llamada al servicio para obtener los datos del usuario
    }

    private String extractUsernameFromToken(String token) {
        // Aquí deberías extraer el nombre de usuario del token JWT usando alguna librería como jjwt o tu propia lógica
        // Ejemplo (esto es solo un esquema, no es funcional):
        return "admin";  // Esto debe ser reemplazado por el código que decodifica el token y extrae el 'username'
    }
}
