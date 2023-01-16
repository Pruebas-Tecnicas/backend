package com.bim.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bim.service.IUsuarioService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private IUsuarioService service;
	
	@GetMapping("/login/{username}/{password}")
	public ResponseEntity<?> login(@PathVariable String username, @PathVariable String password) {
		String token;
		
		Map<String, Object> response = new HashMap<>();
		
		// Validar si hay un error de parte de la base de datos
		try {
			token = service.login(username, password);
		} catch (DataAccessException e) {
			response.put("mensaje", "Por favor contacte al administrador");
			response.put("error", Objects.requireNonNull(e.getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (token.equals("")) {
			response.put("mensaje", "Credenciales inválidas");
			response.put("error", "Error al crear el token");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		
		if (token.equals("Activo")) {
			response.put("mensaje", "Ya ha iniciado sesión");
			response.put("error", "Error al crear el token");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

		response.put("mensaje", "Acceso permitido");
		response.put("token", token);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/logout/{username}")
	public ResponseEntity<?> logout(@PathVariable String username) {
		String result;
		
		Map<String, Object> response = new HashMap<>();
		
		// Validar si hay un error de parte de la base de datos
		try {
			result = service.logout(username);
		} catch (DataAccessException e) {
			response.put("mensaje", "Por favor contacte al administrador");
			response.put("error", Objects.requireNonNull(e.getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (result.equals("")) {
			response.put("mensaje", "Credenciales inválidas");
			response.put("error", "El usuario".concat(username).concat("no está registrado"));
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		
		if (result.equals("Inactivo")) {
			response.put("mensaje", "Solicitud denegada");
			response.put("error", "El usuario no tiene una sesión activa");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

		response.put("mensaje", "Saliendo");
		response.put("token", result);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
