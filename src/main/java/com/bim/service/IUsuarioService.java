package com.bim.service;

import com.bim.entity.Usuario;

public interface IUsuarioService {
	
	public Usuario save(Usuario usuario);
	
	public String login(String username, String password);
	
}
