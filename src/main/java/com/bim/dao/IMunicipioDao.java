package com.bim.dao;

import org.springframework.data.repository.CrudRepository;

import com.bim.entity.Municipio;

public interface IMunicipioDao extends CrudRepository<Municipio, Short>{

	public Municipio findByNombre(String nombre);
	
}
