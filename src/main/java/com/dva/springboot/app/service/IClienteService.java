package com.dva.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.dva.springboot.app.models.entity.Cliente;
import com.dva.springboot.app.models.entity.Factura;
import com.dva.springboot.app.models.entity.Producto;

public interface IClienteService  {
	List<Cliente> getAll();
	Page<Cliente> getAll(Pageable page);	
	void save(Cliente cliente);
	Cliente findById(Long id);
	void eliminar(Long id);
	List<Producto> findByName(String name);
	
	void saveFactura(Factura factura);
	Factura facturaById(Long id);
	
	Producto productoById(Long id);
	
	void eliminarFactura(Long id);
	
	Factura facturaA(Long id);
	
	Cliente clienteWhitFactura(Long id);
}
