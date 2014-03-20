package br.com.lopes.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.PersistenceException;
import javax.validation.groups.ConvertGroup;

import org.apache.log4j.Logger;

import br.com.lopes.dao.PessoaDAO;
import br.com.lopes.model.Pessoa;
import br.com.lopes.model.PessoaFisica;
import br.com.lopes.model.PessoaJuridica;
import br.com.lopes.model.Telefone;
import br.com.lopes.util.Exceptions;
import br.com.lopes.util.FacesUtils;

@ManagedBean(name="pessoaBean")
@ViewScoped
public class PessoaBean implements Serializable, Validator{

	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa;
	private PessoaFisica fisica;
	private PessoaJuridica juridica;	
	private PessoaDAO pessoaDao;
	
	private boolean novoCliente;
	private boolean editarCliente;
	
	private char tipoPessoa = 'F'; // Default
	private String nome;
	
	private List<Pessoa> pessoas;
	
	@ManagedProperty(value="#{telefoneBean}")
	private TelefoneBean telefoneBean;

	private UIComponent form;

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
		
		log.info("init " + idPessoa);
	
		editarCliente = false;
		
		if (idPessoa != null && !idPessoa.equals(0)){
			editarCliente = true;
			
			Pessoa pessoa = pessoaDao.getById(Long.parseLong(idPessoa));

			telefoneBean.setTelefones(pessoa.getTelefones());
			
			log.info(pessoa.getTelefones());
			
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
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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


	public char getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(char tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isPessoaFisica(){		
		return tipoPessoa == 'F';
	}
	
	public boolean isPessoaJuridica(){
		return tipoPessoa == 'J';
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
	
	public boolean isCpfCadastrado(String cpf){
		
		PessoaFisica outraPessoaFisica = pessoaDao.getFisicaByCPF(cpf);

		if (outraPessoaFisica != null && !outraPessoaFisica.equals(this.fisica)){
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
			
			//pessoa = pessoaDao.getById(pessoa.getId());
			pessoaDao.insert(pessoa);
			
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente "+ pessoa + " inserido com sucesso!", null);  
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        
		}catch(PersistenceException e){
			log.error("Falha ao registrar a pessoa " + pessoa);
			String msg = Exceptions.messageSQLExceptionFromPersistenceException(e); 
		    FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha:"+ msg, null);  
		    FacesContext.getCurrentInstance().validationFailed();
		    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		    
		    return "novo-cliente.xhtml";
		}

        return "lista-clientes.xhtml?faces-redirect=true";
		
	}

	public String novoCliente(){
		novoCliente = true;
		return "novo-cliente.xhtml?faces-redirect=true";
	}
	
	public void alterar(ActionEvent event){
		log.info("alterar pessoa");
		
		if(isPessoaFisica()){
			log.info("update fisica");
			pessoaDao.update(fisica);
			
		}else if (isPessoaJuridica()){
			log.info("update juridica");
			pessoaDao.update(juridica);
		}
	}
	
	public String deletar(){
		
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
		
		pessoas = findAll();
		return "lista-clientes.xhtml?faces-redirect=true";
	}
	

	public List<Pessoa> findAll(){
		if (pessoas == null){
			pessoas = pessoaDao.findAll();
		}
		return pessoas;
	}
	
	
	public void cancelar(ActionEvent event){
		FacesContext.getCurrentInstance().responseComplete();
	}

	/**
	 * ****************************************
	 * Métodos para a view
	 * ****************************************
	 */
	
	public String edit(Pessoa pessoa){
		
		/*ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String idPessoa = context.getRequestParameterMap().get("idPessoa");
		
		Pessoa pessoa = pessoaDao.getById(Long.parseLong(idPessoa));
		*/
		editarCliente = false;
		if (pessoa != null){
			telefoneBean.setTelefones(pessoa.getTelefones());
			
			if (pessoa instanceof PessoaFisica){
				fisica = (PessoaFisica) pessoa;
				setTipoPessoa('F');
			}
			else if(pessoa instanceof PessoaJuridica){
				juridica = (PessoaJuridica) pessoa;
				setTipoPessoa('J');
			}
			editarCliente = true;
		}
		
		return "novo-cliente.xhtml";
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
		
		/*
		 * Validação de CPF 
		 * Não pode registrar 2 pessoas com mesmo CPF
		 */
		
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
		return;
	}
	

	public UIComponent getForm() {
		return form;
	}

	public void setForm(UIComponent form) {
		this.form = form;
	}
}
