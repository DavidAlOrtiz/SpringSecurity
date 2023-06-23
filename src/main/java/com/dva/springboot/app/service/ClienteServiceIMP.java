package com.dva.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.dva.springboot.app.DAO.IClienteJPARepositorie;
import com.dva.springboot.app.DAO.IFacturaDao;
import com.dva.springboot.app.DAO.IProductoDAO;
import com.dva.springboot.app.models.entity.Cliente;
import com.dva.springboot.app.models.entity.Factura;
import com.dva.springboot.app.models.entity.Producto;

import jakarta.transaction.Transactional;

@Service
public class ClienteServiceIMP implements IClienteService {
	
	@Autowired
	IClienteJPARepositorie clienteDao;
	@Autowired
	IProductoDAO productoDao;
	@Autowired
	IFacturaDao facturaDao;
	@Override
	public List<Cliente> getAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Override
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	public Page<Cliente> getAll(Pageable page) {
		return clienteDao.findAll(page);
	}

	@Override
	public List<Producto> findByName(String name) {
		return productoDao.finByName(name);
	}

	@Override
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
	}

	@Override
	public Factura facturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	public Producto productoById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminarFactura(Long id) {
		facturaDao.deleteById(id);
	}

	@Override
	public Factura facturaA(Long id) {
		return facturaDao.facturaQueryCustom(id);
	}

	@Override
	public Cliente clienteWhitFactura(Long id) {
		return clienteDao.clientWhitFactura(id);
	}


	


	

	
}
