package br.com.lopes.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

@Entity
public class Telefone {

	@SequenceGenerator(name="enderecoTelefone",sequenceName="seq_telefone")
	@Id @GeneratedValue(generator="enderecoTelefone")
	private long id;
	
	@Column(nullable=false)
	private String ddd;	
	
	@Column(nullable=false)
	private String numero;
	
	@Column(nullable=false)
	private int tipo;
	
	@Transient
	public static int FIXO = 0;
	@Transient
	public static int CELULAR = 1;
	
	@ManyToOne(optional=false)
	@ForeignKey(name="FK_TELEFONE_PESSOA")
	@Index(name="AK_TELEFONE_PESSOA")
	private Pessoa pessoa;
		
	
	/**
	 * Constructor
	 * 
	 */
	
	public Telefone(){
		tipo = FIXO;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}	

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public boolean isTelefoneValido(){
		
		// TODO Fazer validação do ddd/número
		// considerando 0800 e 4004
		
		/*
		if (ddd.length() <> 3){
			return false;
		}
		if (numero)
		*/
		return true;
			
	}
	
	
	@Override
	public String toString() {
		return ddd + '-' + numero;
	}
	
		
	
}
