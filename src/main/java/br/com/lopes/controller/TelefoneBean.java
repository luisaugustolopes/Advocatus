package br.com.lopes.controller;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import br.com.lopes.dao.PessoaDAO;
import br.com.lopes.dao.TelefoneDAO;
import br.com.lopes.model.Pessoa;
import br.com.lopes.model.PessoaFisica;
import br.com.lopes.model.PessoaJuridica;
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
	
	@PostConstruct
	public void init(){
		log.info("init " + telefones);
	}
	
	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		log.info("setTelefone "+ telefone);
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

	public void inserir(ActionEvent actionEvent){
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String idPessoa = context.getRequestParameterMap().get("idPessoa");
		
		if (idPessoa != null && !idPessoa.equals("")){
			
			Pessoa pessoa = new PessoaDAO().getById(Long.parseLong(idPessoa));
			
			telefone.setPessoa(pessoa);
			telefoneDAO.insert(telefone);
			telefones.add(telefone);
			
			telefone = new Telefone();
		}
		
	}
	
	public void acao(ActionEvent actionEvent){
		log.info("acao");
	}
	
	public void deletar(ActionEvent actionEvent){
		
		try {
			log.info("deletar telefone " + telefone);
			telefone = telefoneDAO.getById(telefone.getId());
			telefoneDAO.delete(telefone);
			telefones.remove(telefone);
			telefone = new Telefone();
		} catch (PersistenceException e) {
			String msg = Exceptions.messageSQLExceptionFromPersistenceException(e);
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha:" + msg, null);
			FacesContext.getCurrentInstance().validationFailed();
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
	}	
	
}
