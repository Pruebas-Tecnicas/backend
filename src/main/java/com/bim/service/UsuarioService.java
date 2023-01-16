package com.bim.service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

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
		usuario.setToken("");
		
		return dao.save(usuario);
	}

	@Override
	@Transactional
	public String login(String username, String password) {
		
		Usuario usuarioDb = dao.findByUsername(username);
		String passDb = null;
		
		// Valida que el usuario exista en la base de datos
		if (usuarioDb == null) { 
			return "";
		}
		
		try {
			passDb = new String(Base64.getDecoder().decode(usuarioDb.getPassword()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		// Valida que la contraseña sea correcta
		if (!password.equals(passDb)) {  
			return "";
		}
		
		// Valida que el usuario no tenga un token
		if (usuarioDb.getToken().length() > 0) { 
			return "Activo";
		}
		
		// Colocar token y guardar
		String result = java.util.UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
		usuarioDb.setToken(result);
		
		Usuario usuarioWithToken = dao.save(usuarioDb);		
		
	    return usuarioWithToken.getToken();
	}

	@Override
	@Transactional
	public String logout(String username) {
		Usuario usuarioDb = dao.findByUsername(username);
		
		// Valida que el usuario exista en la base de datos
		if (usuarioDb == null) { 
			return "";
		}
		
		// Valida que el usuario tenga un token
		if (usuarioDb.getToken().length() == 0) { 
			return "Inactivo";
		}
		
		// Quitar token y guardar
		usuarioDb.setToken("");
		dao.save(usuarioDb);		
		
	    return "Su sesión fue cerrada con éxito";
	}
	
}
