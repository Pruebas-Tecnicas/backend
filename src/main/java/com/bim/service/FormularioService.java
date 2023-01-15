package com.bim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bim.dao.ICodigoPostalDao;
import com.bim.dao.IEstadoDao;
import com.bim.dao.IMunicipioDao;
import com.bim.entity.CodigoPostal;
import com.bim.entity.Estado;
import com.bim.entity.Municipio;

import dto.Formulario;
import jakarta.transaction.Transactional;

@Service
public class FormularioService implements IFormularioService {

	@Autowired
	private IEstadoDao estadoDao;
	
	@Autowired
	private IMunicipioDao municipioDao;
	
	@Autowired
	private ICodigoPostalDao codigoPostalDao;
	
	@Override
	@Transactional
	public Formulario save(Formulario formulario) {
		String estadoCap = capitalizar(formulario.getEstado());
		String municipioCap = capitalizar(formulario.getMunicipio());
		
		Estado estado = estadoDao.findByNombre(estadoCap);
		Municipio municipio = municipioDao.findByNombre(municipioCap);
		CodigoPostal codigoPostal = codigoPostalDao.findByCodigo(formulario.getCodigoPostal());
		
		Estado estadoNuevo = new Estado();
		Municipio municipioNuevo = new Municipio();
		CodigoPostal codigoPostalNuevo = new CodigoPostal();
		
		Boolean flag = false;
		
		if (estado == null) {			
			estadoNuevo.setNombre(estadoCap);
			estadoNuevo = estadoDao.save(estadoNuevo);
			
			flag = true;
		}
		
		if (municipio == null) {
			String estadoID = (estado == null) ? estadoNuevo.getId().toString() : estado.getId().toString();
			
			municipioNuevo.setNombre(municipioCap);
			municipioNuevo.setEstadoID(estadoID);
			municipioNuevo = municipioDao.save(municipioNuevo);
			
			flag = true;
		}
		
		if (codigoPostal == null) {			
			String municipioID = (municipio == null) ? municipioNuevo.getId().toString() : municipio.getId().toString();
			
			codigoPostalNuevo.setCodigo(formulario.getCodigoPostal());
			codigoPostalNuevo.setMunicipioID(municipioID);
			codigoPostalDao.save(codigoPostalNuevo);
			
			flag = true;
		}
		
		if (!flag) {
			return null;
		}
		
		Formulario captura = new Formulario();
		captura.setEstado(estadoCap);
		captura.setMunicipio(municipioCap);
		captura.setCodigoPostal(formulario.getCodigoPostal());
		
		return captura;
	}
	
	/**
	 * Coloca en mayuscula la primera letra de cada palabra dejando el resto en minúscula.
	 * @param cadena variable que contiene la frase que se desea capitalizar.
	 * @return cadena de entrada ya capitalizada.
	 */
	public static String capitalizar(String cadena) {
		String[] separadaPorEspacios = cadena.toLowerCase().split(" ");
		StringBuilder sb = new StringBuilder();
		
		for (int indice = 0; indice < separadaPorEspacios.length; indice++) {
			String palabra = separadaPorEspacios[indice];
			
			if (!palabra.equals("de") && !palabra.equals("del")) {
				char primeraLetra = palabra.charAt(0);
				
				sb.append(Character.toUpperCase(primeraLetra));
				sb.append(palabra.substring(1));
				
				if (indice < separadaPorEspacios.length - 1) {
					sb.append(" ");
				}
			} else {
				sb.append(palabra.concat(" "));
			}
		}

		return sb.toString().trim();
	}
}
