package com.example.demo.java1.Sesion;

import com.example.demo.java1.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/logueo")
    public Object login(@RequestBody Usuario login) {
        boolean success = authService.login(login);
        if (success) {
            return Sesion.usuarioLogueado;
        } else {
            return "Credenciales incorrectas";
        }
    }

    @PostMapping("/logout")
    public String logout() {
        authService.logout();
        return "Sesión cerrada correctamente.";
    }
}
