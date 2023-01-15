package com.bim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bim.dao.IUsuarioDao;
import com.bim.entity.Usuario;

@Service
public class UsuarioService implements IUsuarioService {

	@Autowired
	private IUsuarioDao dao;

	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
		usuario.setUsername(usuario.getUsername().toLowerCase().trim());
		usuario.setEmail(usuario.getEmail().toLowerCase().trim());
		
		return dao.save(usuario);
	}
	
}
