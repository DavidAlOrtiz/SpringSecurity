package com.dva.springboot.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dva.springboot.app.DAO.ClienteDAO;
import com.dva.springboot.app.models.entity.Cliente;

@RestController
@RequestMapping("/usuarioRest")
public class UsuarioController {
	
	@Autowired
	private ClienteDAO clienteService;
	
	@GetMapping({"/","/index"})
	public List<Cliente> usuarioGetAll(){
		return clienteService.getAll();
	}
	
	@PostMapping("/guardar")
	public String guardar(@RequestBody  String nombre) {
		try {
			Cliente cliente = new Cliente();
			cliente.setNombre(nombre);
			clienteService.save(cliente);
		} catch (Exception e) {
			return e.getMessage();
		}
		return "guardado correctamente";
	}
}
