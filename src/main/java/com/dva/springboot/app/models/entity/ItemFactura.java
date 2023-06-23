package com.dva.springboot.app.models.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "items_factura")
@Data
public class ItemFactura implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer cantidad;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "producto_id")
	private Producto producto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * 
	 */
	
	public Double calcularImporte() {
		System.out.print("Valor=======" + this.cantidad.doubleValue() * this.producto.getPrecio());
		return this.cantidad.doubleValue() * this.producto.getPrecio();
	}
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "ItemFactura [id=" + id + ", cantidad=" + cantidad + ", producto=" + producto + "]";
	}
 
}
