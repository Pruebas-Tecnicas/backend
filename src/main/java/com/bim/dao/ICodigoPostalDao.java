package com.bim.dao;

import org.springframework.data.repository.CrudRepository;

import com.bim.entity.CodigoPostal;

public interface ICodigoPostalDao extends CrudRepository<CodigoPostal, Short>{

	public CodigoPostal findByCodigo(String codigo);
	
}
