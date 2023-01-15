package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Formulario {
	
	@Size(max = 30, message = "El estado puede contener máximo 30 caracteres")
	@NotBlank(message = "El estado está vacío")
	private String estado;
	
	@Size(max = 30, message = "El municipio puede contener máximo 30 caracteres")
	@NotBlank(message = "El municipio está vacío")
	private String municipio;
	
	@Pattern(regexp = "[0-9]{5}", message = "El formato del código postal no es válido")
	@NotBlank(message = "El código postal está vacío")
	private String codigoPostal;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
}
