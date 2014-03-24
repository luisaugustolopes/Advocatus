package br.com.lopes.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import br.com.lopes.dao.PessoaDAO;
import br.com.lopes.model.Pessoa;
import br.com.lopes.model.PessoaFisica;
import br.com.lopes.model.PessoaJuridica;
import br.com.lopes.model.Telefone;
import br.com.lopes.util.Exceptions;

@ManagedBean(name="pessoaBean")
@ViewScoped
public class PessoaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private PessoaFisica fisica;
	private PessoaJuridica juridica;	
	private PessoaDAO pessoaDao;
	
	private boolean novoCliente;
	private boolean editarCliente;
	
	private char tipoPessoa = 'F'; // Default
	
	private List<Pessoa> pessoas;
	
	@ManagedProperty(value="#{telefoneBean}")
	private TelefoneBean telefoneBean;
	
	private Logger log = Logger.getLogger(getClass());


	/**
	 *  Constructor
	 */
	public PessoaBean() {
		fisica = new PessoaFisica();
		juridica = new PessoaJuridica();		
		pessoaDao = new PessoaDAO();		
	}	
	
	@PostConstruct
	public void init(){
		pessoas = findAll();
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String idPessoa = context.getRequestParameterMap().get("idPessoa");
		String paramNovo = context.getRequestParameterMap().get("novo");
		
		novoCliente = Boolean.valueOf(paramNovo);
	
		editarCliente = (idPessoa != null && !idPessoa.equals(0));
		
		if (editarCliente){			
			
			Pessoa pessoa = pessoaDao.getById(Long.parseLong(idPessoa));

			telefoneBean.setTelefones(pessoa.getTelefones());			
			
			if (pessoa instanceof PessoaFisica){
				fisica = (PessoaFisica) pessoa;
				setTipoPessoa('F');
			}
			else if(pessoa instanceof PessoaJuridica){
				juridica = (PessoaJuridica) pessoa;
				setTipoPessoa('J');
			}
		}
		
	}
	

	/**
	 * ****************************************
	 * Métodos getters/setters 
	 * ****************************************
	 */
	public long getPessoaID(){
		
		long id = 0;
		if (isPessoaFisica()){
			id = fisica.getId();
		}
		else if(isPessoaJuridica()){
			id = juridica.getId();
		}
		
		return id;
	}
	
	public Pessoa getPessoa(){
		Pessoa pessoa = null;
		if (isPessoaFisica()){
			pessoa = (Pessoa)fisica;
		}else if (isPessoaJuridica()){
			pessoa = (Pessoa)juridica;
		}
		return pessoa;
	}
	public PessoaFisica getFisica() {
		return fisica;
	}

	public void setFisica(PessoaFisica pessoaFisica) {
		this.fisica = pessoaFisica;
	}

	public PessoaJuridica getJuridica() {
		return juridica;
	}

	public void setJuridica(PessoaJuridica pessoaJuridica) {
		this.juridica = pessoaJuridica;
	}

	public boolean isNovoCliente() {
		return novoCliente;
	}


	public void setNovoCliente(boolean novoCliente) {
		this.novoCliente = novoCliente;
	}


	public boolean isEditarCliente() {
		return editarCliente;
	}

	public void setEditarCliente(boolean editarCliente) {
		this.editarCliente = editarCliente;
	}

	public char getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(char tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}	

	public boolean isPessoaFisica(){		
		return tipoPessoa == 'F';
	}
	
	public boolean isPessoaJuridica(){
		return tipoPessoa == 'J';
	}

	/*
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setPessoaFisicaJuridica(Pessoa pessoa){
		
		log.info("set pessoa " + pessoa);
		FacesUtils.cleanSubmittedValues(getForm());		
		if( pessoa instanceof PessoaFisica){			
		    this.fisica = (PessoaFisica) pessoa;
			setTipoPessoa('F');
			setNome(fisica.toString());
		}
		else{
			this.juridica = (PessoaJuridica) pessoa;
			setTipoPessoa('J');
			setNome(juridica.toString());
		}
		
	}
	*/
	
	public boolean isCpfCadastrado(String cpf){
		
		PessoaFisica outraPessoaFisica = pessoaDao.getFisicaByCPF(cpf);
		
		if (outraPessoaFisica != null && !outraPessoaFisica.equals(this.fisica)){
			log.info("CPF: " + cpf + " pertence a: " + outraPessoaFisica);
	        return true;
		}
		return false;
	}
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	

	public TelefoneBean getTelefoneBean() {
		return telefoneBean;
	}


	public void setTelefoneBean(TelefoneBean telefoneBean) {
		this.telefoneBean = telefoneBean;
	}


	/**
	 * ****************************************
	 * Métodos de negócio
	 * ****************************************
	 */	
	public String inserir(Pessoa pessoa){

		log.info("Inserir pessoa " + pessoa);
		
		try{
					
			if(novoCliente){

				List<Telefone> telefones = new ArrayList<>();

				// Telefone fixo
				if (!telefoneBean.getTelefoneCadastro()[0].getDdd().isEmpty() && !telefoneBean.getTelefoneCadastro()[0].getNumero().isEmpty()){
					telefones.add(telefoneBean.getTelefoneCadastro()[0]);				
				}
				// Telefone celular
				if (!telefoneBean.getTelefoneCadastro()[1].getDdd().isEmpty() && !telefoneBean.getTelefoneCadastro()[1].getNumero().isEmpty()){
					telefones.add(telefoneBean.getTelefoneCadastro()[1]);				
				}
				
				for (Telefone telefone : telefones) {
					telefone.setPessoa(pessoa);
				}
				
				pessoa.setTelefones(telefones);
			}
			
			pessoaDao.insert(pessoa);
			
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente "+ pessoa + " inserido com sucesso!", null);  
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        
		}catch(PersistenceException e){
			String msg = Exceptions.messageSQLExceptionFromPersistenceException(e); 
			log.error("Falha ao inserir pessoa " + pessoa + " Error:" + msg);
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha:"+ msg, null);  
		    FacesContext.getCurrentInstance().validationFailed();
		    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		    
		    return "cliente.xhtml";
		}

        return "lista-cliente.xhtml?faces-redirect=true";
		
	}

	
	public String gravar(){		
		try{
			
			String msg = null;
			
			if (isPessoaFisica()) {
				log.info("Gravar pessoa física " + fisica);
				pessoaDao.update(fisica);
				msg = "Cliente " + fisica + " atualizado com sucesso";

			} else if (isPessoaJuridica()) {
				log.info("Gravar pessoa jurídica " + juridica);
				pessoaDao.update(juridica);
				msg = "Cliente " + juridica + " atualizado com sucesso";
			}
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,msg,null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			
			
		}catch(PersistenceException e){
			String msg = Exceptions.messageSQLExceptionFromPersistenceException(e);
			log.error("Falha ao gravar pessoa. Error:" + msg);
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha:"+ msg, null);  
		    FacesContext.getCurrentInstance().validationFailed();
		    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		    
		    return "cliente.xhtml";
		}

        return "lista-cliente.xhtml?faces-redirect=true";
	}
	
	public String excluir(){
		
		if (isPessoaFisica()){
			log.info("delete fisica");
			fisica = (PessoaFisica) pessoaDao.getById(fisica.getId());
			pessoaDao.delete(fisica);
			pessoas.remove(fisica);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exclusão de "+ fisica + " realizada com sucesso!",null);
			FacesContext.getCurrentInstance().addMessage(null, message);		

		}else if(isPessoaJuridica()){
			log.info("delete juridica");
			juridica = (PessoaJuridica) pessoaDao.getById(juridica.getId());
			pessoaDao.delete(juridica);
			pessoas.remove(juridica);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exclusão de "+ juridica + " realizada com sucesso!",null);
			FacesContext.getCurrentInstance().addMessage(null, message);		
		}
		
		pessoas = null;
		pessoas = findAll();
		return "lista-cliente.xhtml?faces-redirect=true";
	}
	

	public List<Pessoa> findAll(){
		if (pessoas == null){
			pessoas = pessoaDao.findAll();
		}
		return pessoas;
	}
	

	/**
	 * ****************************************
	 * Métodos para a view
	 * ****************************************
	 */	
	
	/*
	 * Validação de CPF 
	 * Não pode registrar 2 pessoas com mesmo CPF
	*/
	/*
	@Override
	public void validate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
		
		
		log.info("validate CPF");
		
		
		UIComponent outroComponente; 
		outroComponente = component.findComponent("cpf");
					
		if (outroComponente.equals(component)){
			
			String cpf = object.toString();
			
			if (isCpfCadastrado(cpf)){
				
				FacesContext.getCurrentInstance().validationFailed();
				
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "CPF " + cpf + " já está cadastrado!", null);
				throw new ValidatorException(facesMessage);
			}
		}
		
	}
	*/
}
