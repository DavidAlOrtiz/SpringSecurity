package com.dva.springboot.app.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dva.springboot.app.models.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long> {
	
	@Query("select f from Factura f  left join fetch f.cliente c left join fetch f.itemsFactura i  left join fetch i.producto p where f.id=1")
	public Factura facturaQueryCustom(Long id);
	
}
