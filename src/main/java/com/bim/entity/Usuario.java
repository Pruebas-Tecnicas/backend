package com.bim.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuarioID")
	private Short id;

	@Pattern(regexp = "[a-z]{5,15}", message = "El nombre de usuario debe contener entre 5 y 15 letras minúsculas")
	private String username;
	
	@Size(max = 20, message = "El correo no debe tener mas de 20 caracteres")
	@Email(message = "Se debe ingresar un correo electronico válido")
	private String email;

	@NotBlank(message = "La contraseña está vacía")
	@Size(min = 8, max = 60, message = "La contraseña debe contener entre 8 y 60 caracteres.")
	@Column(name = "contrasena")
	private String password;
	
	@Column(name = "token")
	private String token;

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
