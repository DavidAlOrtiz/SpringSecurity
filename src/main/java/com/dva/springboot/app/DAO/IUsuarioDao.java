package com.dva.springboot.app.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import com.dva.springboot.app.models.entity.Usuario;

@Component
public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
	public Usuario findUserByUsername(String username);
}
