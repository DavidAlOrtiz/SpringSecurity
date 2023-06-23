package com.dva.springboot.app.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dva.springboot.app.models.entity.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Repository
public class ClienteDaoIMP implements ClienteDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly = true)
	@Override
	public List<Cliente> getAll() {
		return em.createQuery("from Cliente").getResultList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		Cliente cliente = null;
		if(id != null && id > 0) {
			cliente = em.find(Cliente.class, id);
		}
		return cliente;
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		try {
			if(cliente.getId() != null && cliente.getId() > 0) {
				System.out.println(cliente.getId());
				em.merge(cliente);
			}else {
				em.persist(cliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		try {
			em.remove(this.findById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
