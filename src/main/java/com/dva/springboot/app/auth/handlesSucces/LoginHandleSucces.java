package com.dva.springboot.app.auth.handlesSucces;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LoginHandleSucces extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		SessionFlashMapManager flasManager = new SessionFlashMapManager();
		
		if(authentication != null) {
			logger.info(authentication.getName());
		}
		
		FlashMap flasMap = new FlashMap();
		flasMap.put("success", "Ha iniciado sesion correctamente!");
		flasManager.saveOutputFlashMap(flasMap, request, response);
		// TODO Auto-generated method stub
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
