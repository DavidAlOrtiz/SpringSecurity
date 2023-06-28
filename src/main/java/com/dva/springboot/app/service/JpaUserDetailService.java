package com.dva.springboot.app.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dva.springboot.app.DAO.IUsuarioDao;
import com.dva.springboot.app.models.entity.Usuario;
import org.springframework.transaction.annotation.Transactional;

@Service("jpaUserDetailService")
public class JpaUserDetailService implements UserDetailsService {

	@Autowired
	IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDao.findUserByUsername(username);
		Logger log = LoggerFactory.getLogger(JpaUserDetailService.class);
		
		if(usuario == null) {
			log.info("El usuario " +usuario.getUsername() + "No existe");
			throw  new UsernameNotFoundException("el usuario no existe");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		usuario.getRoles().forEach((roles)->{
			System.err.println(roles.getAuthority());
			authorities.add(new SimpleGrantedAuthority(roles.getAuthority()));
		});
		
		if(authorities.isEmpty()) {
			log.error("La lista de los roles esta vacia, Intentalo mas tarde");
			throw new UsernameNotFoundException("La lista de roles esta vacia");
		}
		
		return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, true, authorities);
	}

}
