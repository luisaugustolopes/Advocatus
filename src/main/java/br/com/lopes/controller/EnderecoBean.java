package br.com.lopes.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import br.com.lopes.dao.EnderecoDAO;
import br.com.lopes.dao.PessoaDAO;
import br.com.lopes.model.Cidade;
import br.com.lopes.model.Endereco;
import br.com.lopes.model.Pessoa;
import br.com.lopes.model.Telefone;
import br.com.lopes.util.Exceptions;

@ManagedBean
@ViewScoped
public class EnderecoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EnderecoDAO enderecoDAO;
	private Endereco endereco;
	
	
	private Logger log = Logger.getLogger(getClass());
	
	/**
	 * ****************************************
	 * Constructor
	 * ****************************************
	 */
	public EnderecoBean(){
		setEndereco(new Endereco());
		enderecoDAO = new EnderecoDAO();
		endereco.setCidade(new Cidade());
		endereco.getCidade().setEstado("SP");
		
	}

	
	/**
	 * ****************************************
	 * M�todos getters/setters 
	 * ****************************************
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	

	/**
	 * ****************************************
	 * M�todos de neg�cio
	 * ****************************************
	 */

	/**
	 * Inserir novos endere�os � Pessoa
	 * Inicialmente este m�todo n�o ser� utilizado pois a Pessoa ter� apenas 1 endere�o
	 * sendo registrado no cadastro.
	 */
	public void inserir(){

		try{
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			String idPessoa = context.getRequestParameterMap().get("idPessoa");
			
			if (idPessoa != null && !idPessoa.equals("")){
				
				Pessoa pessoa = new PessoaDAO().getById(Long.parseLong(idPessoa));
				
				endereco.setPessoa(pessoa);
				enderecoDAO.insert(endereco);
				String msg = "Endere�o "+endereco+" inclu�do com sucesso"; 
				endereco = new Endereco();
				
				
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, msg ,null);
				FacesContext.getCurrentInstance().addMessage(null, facesMessage);				
			}
		} catch (PersistenceException e) {
			String msg = Exceptions.messageSQLExceptionFromPersistenceException(e);
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha:" + msg, null);
			FacesContext.getCurrentInstance().validationFailed();
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
		
	}
	
	

}
