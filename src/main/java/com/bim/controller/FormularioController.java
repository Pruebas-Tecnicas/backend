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

import com.bim.service.IFormularioService;

import dto.Formulario;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/formulario")
public class FormularioController {

	@Autowired
	private IFormularioService service;
	
	@PostMapping("/guardar")
	public ResponseEntity<?> insertarFormulario(@Valid @RequestBody Formulario formulario, BindingResult result) {

		Formulario captura;
		Map<String, Object> response = new HashMap<>();
		
		// Validar si hay errores de parte de las anotaciones de validación
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
			captura = service.save(formulario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al guardar los datos");
			response.put("error", Objects.requireNonNull(e.getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		} catch (NullPointerException e) {
			response.put("mensaje", "Error al guardar los datos");
			response.put("error", Objects.requireNonNull(e.getMessage()));
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

		response.put("mensaje", "Los datos han sido guardados con éxito");
		response.put("captura", captura);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
}
