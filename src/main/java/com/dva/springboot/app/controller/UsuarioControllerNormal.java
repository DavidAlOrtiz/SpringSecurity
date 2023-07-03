package com.dva.springboot.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;

@Controller
@RequestMapping("/usuario")
@SessionAttributes("cliente")
public class UsuarioControllerNormal {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	protected final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	IClienteService clienteService;
	
	@Autowired
	MessageSource mesaMessageSource;
	
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
	public String index(@RequestParam(name = "pageA", defaultValue = "0")  int page, Model model,
			Authentication authentication, HttpServletRequest request, Locale locale) {
		Pageable pageRequest = PageRequest.of(page, 1);
		Page<Cliente> clienteReq =  clienteService.getAll(pageRequest);
		
		Authentication aut = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication != null ) {
			log.info("Inicio sesion " + authentication.getName());
		}
		
		if(hasRole("ADMIN")) {
			logger.info("Hola tienes acceso".concat(authentication.getName()).concat("Tienes Acceso") );
		}else {
			logger.info("No tienes acceso");
		}
		
		SecurityContextHolderAwareRequestWrapper contextWrap = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
		if(contextWrap.isUserInRole("ADMIN")) {
			logger.info("Tienes Acceso desde el SegurityContextHolderHande");
		}else {
			logger.info("No tienes acceso");
		}
		
		PageRender<Cliente> pageRender = new PageRender<>("/usuario/", clienteReq);
		model.addAttribute("pageA", pageRender);
		model.addAttribute("titulo", mesaMessageSource.getMessage("text.cliente.titulo", null, locale));
		model.addAttribute("usuarios", clienteReq);
		return "listar";
	}
	
	@Secured({"ROLE_ADMIN"})
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
	
	@Secured("ROLE_ADMIN")
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
	
	private boolean hasRole(String role) {
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if( securityContext == null ) {
			return false;
		}
		
		Authentication auth = securityContext.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		for(GrantedAuthority authoritiesA : authorities ) {
			if(role.equals(authoritiesA.getAuthority())) {
				logger.info("Hola usuario tu rol es");
				return true;
			}
		}
		
		return false;
	}
	
//	private boolean hasRole(String role) {
//		SecurityContext sec = SecurityContextHolder.getContext();
//		
//		if(sec == null ) {
//			return false;
//		}
//		
//		Authentication aut = sec.getAuthentication();
//		
//		if(aut == null) {
//			return false;
//		}
//		
//		Collection<? extends GrantedAuthority> authoris = aut.getAuthorities();
//		
//		for(GrantedAuthority autA : authoris) {
//			if(role.equals(autA.getAuthority())) {
//				return true;
//			}
//		}
//		
//		return false;
//		
//	}
}
