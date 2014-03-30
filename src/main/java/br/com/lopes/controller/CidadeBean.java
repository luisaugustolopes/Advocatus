package br.com.lopes.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

import br.com.lopes.dao.CidadeDAO;
import br.com.lopes.model.Cidade;

@ManagedBean
@ApplicationScoped
public class CidadeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(getClass());
	
	private CidadeDAO cidadeDAO;
	private List<Cidade> cidades;
	
	private String[] estados = {"AC","AL","AM","AP","BA","CE","DF","ES","GO","MA","MG","MS","MT","PA","PB","PE","PI","PR","RJ","RN","RO","RR","RS","SC","SE","SP","TO"};
	
	
	/**
	 * Constructor
	 */
	public CidadeBean(){
		cidadeDAO = new CidadeDAO();
	}


	/**
	 * ****************************************
	 * Métodos getters/setters 
	 * ****************************************
	 */
	
	/**
	 * Lista de cidades
	 * @return List<Cidade>
	 */
	public List<Cidade> getCidades() {		
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}


	/**
	 * Vetor de estados
	 * @return String[]
	 */
	public String[] getEstados() {
		return estados;
	}


	public void setEstados(String[] estados) {
		this.estados = estados;
	}

	
	/**
	 * ****************************************
	 * Métodos negócio 
	 * ****************************************
	 */
	
	/**
	 * Buscar as cidades pertencentes ao estado
	 * @param estado
	 */
	public void buscarCidadesDoEstado(String estado) {  
        if(estado !=null && !estado.equals(""))  
            cidades = cidadeDAO.getCidadesPorEstado(estado);
    }  	
	

}
