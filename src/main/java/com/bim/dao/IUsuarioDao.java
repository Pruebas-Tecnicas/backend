package com.bim.dao;

import org.springframework.data.repository.CrudRepository;

import com.bim.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Short>{

	public Usuario findByUsername(String username);
	
}
