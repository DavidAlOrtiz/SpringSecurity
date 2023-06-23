package com.dva.springboot.app.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dva.springboot.app.models.entity.Cliente;
import com.dva.springboot.app.models.entity.Factura;
import com.dva.springboot.app.models.entity.ItemFactura;
import com.dva.springboot.app.models.entity.Producto;
import com.dva.springboot.app.service.IClienteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaControler {
	
	@Autowired
	IClienteService clienteService;
	
	private final static Logger log = LoggerFactory.getLogger( "FacturaControler" );
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(required = false) Long id , Model model, RedirectAttributes flas) {
		Factura factura = clienteService.facturaA(id); //clienteService.facturaById(id); //
//		if(factura != null) {
//			flas.addFlashAttribute("error", "El usuario no existe");
//			return "redirect:/listar";
//		}
		System.err.println(factura);
		model.addAttribute("factura", factura);
		model.addAttribute("tituli", "Hola");
		return "factura/ver";
	}
	
	
	
	@GetMapping("/form/{id}")
	public String crear(@PathVariable Long id, Model model, RedirectAttributes flash) {
		
		Cliente cliente =clienteService.findById(id);
		if(cliente ==  null ) {
			flash.addFlashAttribute("error", "Usuario no encontrado");
			return "redirect:/listar";
		}
		
		Factura factura = new Factura();
		factura.setClient(cliente);
		
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Factura formulario");
		return "factura/form";
	}
	
	@GetMapping(value="/cargar-orden/{term}", produces = {"application/json"})
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
		return clienteService.findByName(term);
	}
	
	
	@PostMapping("/form")
	public String guardar(@Valid Factura factura, 
			BindingResult result, Model model , @RequestParam(required = false) Long[] itemId,
			@RequestParam(name = "catidad[]", required = false) Integer[] cantidad,
			RedirectAttributes flas, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario");
			return "/factura/form";
		}
		if(itemId == null || itemId.length > 0 ) {
			model.addAttribute("titulo", "Formulario");
			model.addAttribute("error", "error");
			return "/factura/form";
		}
		
		
		for(int i = 0; i < itemId.length ; i++) {
			Producto producto = clienteService.productoById(itemId[i]);
			ItemFactura itemFactura = new ItemFactura();
			itemFactura.setCantidad(cantidad[i]);
			itemFactura.setProducto(producto);
			log.warn("El id del producto" + itemId[i]);
			log.warn("Con la cantidad " + cantidad[i]);
			log.warn("del producto  " + producto);
			
		}
		clienteService.saveFactura(factura);
		status.setComplete();
		flas.addFlashAttribute("success", "Factura creada con exico");
		return "redirect:/usuario/ver/" + factura.getClient().getId();
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		Factura factura = clienteService.facturaById(id);
		if(factura != null) {
			clienteService.eliminarFactura(id);
			flash.addFlashAttribute("Factura eliminada con exiito");
			return "redirect:/ver/"+factura.getClient().getId();
		}
		flash.addFlashAttribute("error", "La factura no existe en la base de datos");
		return "redirect:/cliente/listar";
		
	}
	
}
