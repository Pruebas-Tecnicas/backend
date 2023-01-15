package com.bim.dao;

import org.springframework.data.repository.CrudRepository;

import com.bim.entity.Estado;

public interface IEstadoDao extends CrudRepository<Estado, Short>{

	public Estado findByNombre(String nombre);
	
}
