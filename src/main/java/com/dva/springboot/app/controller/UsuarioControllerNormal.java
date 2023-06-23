package com.dva.springboot.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dva.springboot.app.DAO.ClienteDAO;
import com.dva.springboot.app.controller.util.paginator.PageRender;
import com.dva.springboot.app.models.entity.Cliente;
import com.dva.springboot.app.service.IClienteService;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;

@Controller
@RequestMapping("/usuario")
@SessionAttributes("cliente")
public class UsuarioControllerNormal {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	IClienteService clienteService;
	
	
	
	@GetMapping("/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename){
		log.info(filename);
		Path path = Paths.get("uploads").resolve(filename).toAbsolutePath();
		log.error(path.toString());
		Resource resource = null;
		try {
			resource = new UrlResource(path.toUri());
			if(!resource.exists() || !resource.isReadable()) {
				throw new RuntimeException("No se puede cargar la Imagen" + path.getFileName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.print(ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ resource.getFilename() +"\"")
				.body(resource));
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ resource.getFilename() +"\"")
				.body(resource);
	}
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model model, RedirectAttributes flas) {
		
		Cliente cliente = clienteService.clienteWhitFactura(id); //clienteService.findById(id);
		if(cliente == null) {
			flas.addFlashAttribute("error","El cliente no existe");
			return "redirect:/usuario/";
		}
		
		model.addAttribute("usuario", cliente);
		model.addAttribute("titulo", "detalles");
		return "ver";
	}
	
	@GetMapping({"/", "/listar"})
	public String index(@RequestParam(name = "pageA", defaultValue = "0")  int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 1);
		Page<Cliente> clienteReq =  clienteService.getAll(pageRequest);
		
		PageRender<Cliente> pageRender = new PageRender<>("/usuario/", clienteReq);
		model.addAttribute("pageA", pageRender);
		model.addAttribute("titulo", "Hola La lista esta aqui");
		model.addAttribute("usuarios", clienteReq);
		return "listar";
	}
	
	@GetMapping("/form")
	public String formulario(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		System.out.print(cliente.getCreateAt());
		model.put("titulo", "Formulario para agregar al cliente");
		return "form";
	}
	
	@GetMapping("/form/{id}")
	public String formularioById(@PathVariable Long id, Map<String, Object> model) {
		model.put("cliente", clienteService.findById(id));
		model.put("titulo", "Formulario para agregar al cliente");
		return "form";
	}
	
	
	@PostMapping("/form")
	public String guardar(@Valid  Cliente cliente,
			BindingResult result, Model model,@RequestParam("file") MultipartFile file  , RedirectAttributes flash,  SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario");
			return "form";
		
		}
		if(!file.isEmpty()) {
			
			if(cliente.getId() != null && 
			 cliente.getId() > 0 && 
			 cliente.getFoto() != null && 
			 cliente.getFoto().length() > 0) {
				Path rootPath = Paths.get("uploads").resolve(cliente.getFoto()).toAbsolutePath();
				File archivo = rootPath.toFile();
				if(archivo.canRead() && archivo.exists()) {
					archivo.delete();
				}
			}
		
			//Path directorio = Paths.get("src//main//resources//static//upload");
			//String rootPath = "C:\\Users\\david\\Desktop\\proyecto\\upload";
			//Guardar Imagen desde el proyecto 
			String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
			
			Path rootPath = Paths.get("uploads").resolve(uniqueFileName);
			Path rootAbosulute = rootPath.toAbsolutePath();
			log.info(rootPath.toString());
			log.info(rootAbosulute.toString());
			try {
//				byte[] bytes = file.getBytes();
//				Path rutaCompleta = Paths.get(rootPath + "//" + file.getOriginalFilename() );
//				Files.write(rutaCompleta, bytes);
				Files.copy( file.getInputStream(), rootAbosulute);
				flash.addFlashAttribute("info", "se subio correctamente " + uniqueFileName);
				cliente.setFoto(uniqueFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		clienteService.save(cliente);
		System.out.print(cliente.getCreateAt());
		status.setComplete();
		flash.addFlashAttribute("success", "se guardo correctamete");
		return "redirect:/usuario/";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		if(id > 0 && id != null) {
			Cliente cliente = clienteService.findById(id);
			clienteService.eliminar(id);
			flash.addFlashAttribute("success", "se elimino correctamete");
			
			//
			Path path = Paths.get("uploads").resolve(cliente.foto).toAbsolutePath();
			File archivo = path.toFile();
			if( archivo.exists() && archivo.canRead()) {
				if(archivo.delete()) {
					flash.addFlashAttribute("info", "se elimino la imagen");
				}
			}
			
		}
		return "redirect:/usuario/";
	}
}
