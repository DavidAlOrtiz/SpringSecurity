package com.dva.springboot.app.DAO;

import org.springframework.data.repository.CrudRepository;

import com.dva.springboot.app.models.entity.Producto;

public interface IProductoService extends CrudRepository<Producto, Long> {
	
}
