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
		
		if (usuarioDb == null) { // Valida que el usuario exista en la base de datos
			return "";
		}
		
		try {
			passDb = new String(Base64.getDecoder().decode(usuarioDb.getPassword()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if (!password.equals(passDb)) { // Valida que la contraseña sea correcta 
			return "";
		}
		
		if (usuarioDb.getToken().length() > 0) { // Valida que el usuario no tenga ya un token
			return "Activo";
		}
		
		String result = java.util.UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
		usuarioDb.setToken(result);
		
		Usuario usuarioWithToken = dao.save(usuarioDb);		
		
	    return usuarioWithToken.getToken();
	}
	
}
