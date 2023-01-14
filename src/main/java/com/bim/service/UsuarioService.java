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
		return dao.save(usuario);
	}
	
}
