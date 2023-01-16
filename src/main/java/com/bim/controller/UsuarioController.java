package com.bim.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bim.entity.Usuario;
import com.bim.service.IUsuarioService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	private IUsuarioService service;
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) {

		Usuario usuarioNuevo;
		Map<String, Object> response = new HashMap<>();
		
		// Validar si hay errores de parte de las anotaciones de validación de la clase Entity
		if (result.hasErrors()) {

			List<String> errors = new ArrayList<>();

			for (FieldError err : result.getFieldErrors()) {
				errors.add(err.getDefaultMessage());
			}

			response.put("errors", errors);

			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		// Validar si ocurre un error al insertar los datos en la bade de datos
		try {
			usuarioNuevo = service.save(usuario);
		} catch (DataAccessException e) {
			String motivo = Objects.requireNonNull(e.getMessage()).concat(" : ").concat(e.getMostSpecificCause().getMessage());
			
			if (motivo.contains("UQ_usuarios_usuario")) {
				motivo = "El nombre de usuario ingresado no está disponible, debe elegir otro.";
			} else if (motivo.contains("UQ_usuarios_email")) {
				motivo = "El correo electrónico ingresado no está disponible, debe elegir otro.";
			} else {
				motivo = "Por favor notifique al administrador.";
			}
			
			response.put("mensaje", "Error al registrar el usuario");
			response.put("error", motivo);

			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario ha sido registrado con éxito");
		response.put("usuario", usuarioNuevo);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
