package com.dva.springboot.app.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dva.springboot.app.models.entity.Cliente;

public interface IClienteJPARepositorie extends PagingAndSortingRepository<Cliente, Long>,
				CrudRepository<Cliente, Long>{
	
	@Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
	Cliente clientWhitFactura(Long id);

}
