package br.com.lopes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Index;

@Entity
public class Cidade {
 
	@SequenceGenerator(name="cidadeGenerator", sequenceName="seq_cidade")
	@Id @GeneratedValue(generator="cidadeGenerator")
	private long id;
	 
	@Column(nullable=false)
	@Index(name="AK_CIDADE_NOME")	
	private String nome;
	
	@Column(nullable=false)
	@Index(name="AK_CIDADE_ESTADO")
	private String estado;

	@OneToMany(mappedBy="cidade")
	private List<Endereco> endereco;	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Endereco> getEndereco() {
		return endereco;
	}

	public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}
	
	@Override
	public String toString() {
		return nome;
	}
}
 
