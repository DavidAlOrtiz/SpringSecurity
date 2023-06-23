package com.dva.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "facturas")
@Data
public class Factura implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String descripcion;
	private String observacion;
	@Column(name = "creado_at")
	private Date createAt;
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "items_factura")
	private List<ItemFactura> itemsFactura;
	
	

	@PrePersist
	public void agregar() {
		this.createAt = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Cliente getClient() {
		return cliente;
	}

	public void setClient(Cliente client) {
		this.cliente = client;
	}

	public List<ItemFactura> getItemsFactura() {
		return itemsFactura;
	}

	public void setItemsFactura(List<ItemFactura> itemsFactura) {
		this.itemsFactura = itemsFactura;
	}
	
	

	public double getTotal() {
		Double total = 0.0;
		int tamanio = this.itemsFactura.size();
		for(int i=0 ; i < tamanio; i++) {
			total += this.itemsFactura.get(i).calcularImporte();
			System.out.print(this.itemsFactura.get(i).calcularImporte());
		}
		return total;
	}
	private static final long serialVersionUID = 1L;
	public Factura agregarItem(ItemFactura itemFactura) {
		this.itemsFactura.add(itemFactura);
		return this;
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", descripcion=" + descripcion + ", observacion=" + observacion + ", createAt="
				+ createAt + ", cliente=" + cliente + ", itemsFactura=" + itemsFactura + ", getTotal()=" + this.getTotal()
				+ "]";
	}
	

	
	
	
}
