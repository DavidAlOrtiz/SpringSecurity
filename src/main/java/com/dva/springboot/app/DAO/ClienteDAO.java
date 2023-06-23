package com.dva.springboot.app.DAO;

import java.util.List;

import com.dva.springboot.app.models.entity.Cliente;

public interface ClienteDAO {
	List<Cliente> getAll();
	void save(Cliente cliente);
	Cliente findById(Long id);
	void eliminar(Long id);
}
