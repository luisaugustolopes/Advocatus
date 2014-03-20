package br.com.lopes.controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.lopes.dao.UsuarioDao;
import br.com.lopes.model.Usuario;

@ManagedBean
@SessionScoped
public class AutenticacaoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();
	
	private Logger log = Logger.getLogger(getClass());
	
	public Usuario getUsuario(){
		return this.usuario;
	}
	
	public String efetuarLogon(Usuario usuario){	
		
		String msg = null;
		
		UsuarioDao usuarioDao = new UsuarioDao();
		if (usuarioDao.selecionarUsuarioPorLogon(usuario.getLogon()) == null){
			msg = "Usuário não cadastrado!";
		}
		else if(!usuarioDao.ativo(usuario)){			
			msg = "Usuário não está ativo no sistema!";
		}
		else if(!usuarioDao.validarLoginSenha(usuario)){
			msg = "Usuário/senha incorretos!";
		}
		
		if (msg != null) {
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);  
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        
			return "login?faces-redirect=true";
		}
					
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession)ec.getSession(false);
		session.setAttribute("usuario", usuario.getLogon());
		log.info("Login: "+ session.getAttribute("usuario"));
		
		return "menu?faces-redirect=true";
	}
	
	public String logout(){
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession)ec.getSession(false);
		
		log.info("Logout: "+ session.getAttribute("usuario"));		
		session.removeAttribute("usuario");

		return "login?faces-redirect=true";
	}

	public Object logado(Usuario usuario){
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession)ec.getSession(false);		
		return session.getAttribute("usuario");
	}
}
