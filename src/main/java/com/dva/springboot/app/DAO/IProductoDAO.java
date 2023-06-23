package com.dva.springboot.app.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dva.springboot.app.models.entity.Producto;

public interface IProductoDAO extends CrudRepository<Producto, Long> {
	@Query("select p from Producto p  where p.nombre like %?1%")
	public List<Producto> finByName(String term);
	public List<Producto> findByNombreLikeIgnoreCase(String term);
}
