package br.com.lopes.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;

import br.com.lopes.dao.PessoaDAO;
import br.com.lopes.dao.TelefoneDAO;
import br.com.lopes.model.Pessoa;
import br.com.lopes.model.Telefone;
import br.com.lopes.util.Exceptions;

@ManagedBean(name="telefoneBean")
@ViewScoped
public class TelefoneBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Telefone telefone;
	private Telefone[] telefoneCadastro = new Telefone[2];	
	private TelefoneDAO telefoneDAO;
	private List<Telefone> telefones;

	private Logger log = Logger.getLogger(getClass());

	
	/**
	 * Constructor
	 */
	public TelefoneBean(){
		telefoneDAO = new TelefoneDAO();
		
		telefone = new Telefone();
		
		telefoneCadastro[0] = new Telefone();
		telefoneCadastro[1] = new Telefone();
		
		telefoneCadastro[0].setTipo(Telefone.FIXO);
		telefoneCadastro[1].setTipo(Telefone.CELULAR);
	}	

	
	/**
	 * ****************************************
	 * Métodos getters/setters 
	 * ****************************************
	 */	
	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}	
	
	public Telefone[] getTelefoneCadastro() {
		return telefoneCadastro;
	}

	public void setTelefoneCadastro(Telefone[] telefoneCadastro) {
		this.telefoneCadastro = telefoneCadastro;
	}
	
	public String getTipoTelefone(Telefone telefone){
		String tipo = null;
		if (telefone.getTipo() == Telefone.FIXO){
			tipo = "Fixo" ;
		}else if (telefone.getTipo() == Telefone.CELULAR){
			tipo = "Celular";
		}
		
		return tipo;
	}

	public void inserir(){
		
		if (telefone == null || !telefone.isTelefoneValido()){
			String msg = "Informe um número de telefone válido.";
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, msg ,null);
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			return;
		}
			
		try{
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			String idPessoa = context.getRequestParameterMap().get("idPessoa");
			
			if (idPessoa != null && !idPessoa.equals("")){
				
				Pessoa pessoa = new PessoaDAO().getById(Long.parseLong(idPessoa));
				
				telefone.setPessoa(pessoa);
				telefoneDAO.insert(telefone);
				telefones.add(telefone);
				String msg = "Telefone "+telefone+" incluído com sucesso"; 
				telefone = new Telefone();
				
				
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
	
	
	public void gravar(RowEditEvent event) {
		
		Telefone telefone = (Telefone)event.getObject();
		if (telefone != null && this.telefone.isTelefoneValido()){
			
			try{
				telefoneDAO.update(telefone);
				
				String msg = "Telefone atualizado com sucesso!";
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, msg  ,null);
				FacesContext.getCurrentInstance().addMessage(null, facesMessage);
				
			}catch (PersistenceException e) {
				String msg = Exceptions.messageSQLExceptionFromPersistenceException(e);
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha:" + msg, null);
				FacesContext.getCurrentInstance().validationFailed();
				FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			}
			
		}  
    }  
	
	
	public void deletar(Telefone telefone){
		
		try {
			
			this.telefone = telefoneDAO.getById(telefone.getId());
			telefoneDAO.delete(this.telefone);			
			telefones.remove(this.telefone);
			this.telefone = new Telefone();
			
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Telefone " + telefone + " excluído." ,null);
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			
		} catch (PersistenceException e) {
			String msg = Exceptions.messageSQLExceptionFromPersistenceException(e);
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha:" + msg, null);
			FacesContext.getCurrentInstance().validationFailed();
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
	}
	
	public void cancelar(){
		telefone = new Telefone();
	}
	
}
