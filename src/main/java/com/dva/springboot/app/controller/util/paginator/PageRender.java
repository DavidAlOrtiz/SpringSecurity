package com.dva.springboot.app.controller.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {
	
	private String URL;
	private Page<T> page;
	private int elementoPagina;
	private int totalPaginas;
	private int paginaActual;
	private List<PageItem> paginasA;
	
	public PageRender(String URL, Page<T> page) {
		this.URL = URL;
		this.page = page;
		this.elementoPagina = page.getSize();
		this.totalPaginas = page.getTotalPages();
		this.paginaActual = page.getNumber()  + 1;
		this.paginasA = new ArrayList<>();
		
		int desde, hasta;
		if(totalPaginas <= elementoPagina) {
			desde = 1 ;
			hasta = totalPaginas;
		}else {
			if(paginaActual <= elementoPagina) {
				desde = 1;
				hasta = elementoPagina;
				
			}else if(paginaActual >= totalPaginas - elementoPagina/2) {
				desde = totalPaginas - elementoPagina + 1;
				hasta = elementoPagina;
			}else {
				desde = paginaActual -  elementoPagina/2;
				hasta = elementoPagina;
			}
		}
		
		for(int i = 0;  i < hasta; i++ ) {
		 this.paginasA.add(new PageItem((desde + 1), (paginaActual == desde +1)));
		}
		
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

	public int getElementoPagina() {
		return elementoPagina;
	}

	public void setElementoPagina(int elementoPagina) {
		this.elementoPagina = elementoPagina;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}

	public List<PageItem> getPaginasA() {
		return paginasA;
	}

	public void setPaginasA(List<PageItem> paginasA) {
		this.paginasA = paginasA;
	}
	
	public boolean isFirst() {
		return this.page.isFirst();
	}
	
	public boolean isLat() {
		return this.page.isLast();
	}
	
	
	public boolean hasNext() {
		return this.page.hasNext();
	}
	
	public boolean hastPrevius() {
		return this.page.hasNext();
	}
	
	
	
	

 
 
}
