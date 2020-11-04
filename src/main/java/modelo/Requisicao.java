package modelo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import util.Util;

@Entity
@Table(name="REQUISICAO2")

public class Requisicao {
	private Long numero;
	private String nomeDoProduto;
	private String data;
	private Cliente cliente;
	
	public Requisicao(String nomeDoProduto, String data, Cliente cliente){
		super();
		this.nomeDoProduto = nomeDoProduto;
		this.data = data;
		this.cliente = cliente;
		
	}
	
	public Requisicao(){
	}
	
	public String toString(){
		return "Numero = " + numero + " nomeDoProduto = " + nomeDoProduto + " data " + data;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getNomeDoProduto() {
		return nomeDoProduto;
	}

	public void setNomeDoProduto(String nomeDoProduto) {
		this.nomeDoProduto = nomeDoProduto;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CLIENTE_ID")
	public 	Cliente getCliente()
	{	return cliente;
	}
	
	public void setCliente(Cliente cliente)
	{	this.cliente = cliente;
	}

}
