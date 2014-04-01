package br.com.lopes.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import br.com.lopes.dao.EnderecoDAO;
import br.com.lopes.model.Cidade;
import br.com.lopes.model.Endereco;
import br.com.lopes.util.Exceptions;

@ManagedBean
@ViewScoped
public class EnderecoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Endereco endereco;	
	
	
	
	/**
	 * ****************************************
	 * Constructor
	 * ****************************************
	 */
	public EnderecoBean(){		
		endereco = new Endereco();
		
		endereco.setCidade(new Cidade());
		
		//endereco.getCidade().setEstado("SP");		
		
	}	

	
	/**
	 * ****************************************
	 * Métodos getters/setters 
	 * ****************************************
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	

}
