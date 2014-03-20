package br.com.lopes.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import br.com.lopes.model.Pessoa;
import br.com.lopes.model.PessoaFisica;
import br.com.lopes.util.JpaUtils;

public class PessoaDAO extends GenericDAO<Long, Pessoa>{
	
	
	/*
	public Pessoa getPessoa(long id){
		// Registrar pessoa na base
		EntityManager manager = JPAUtil.getEntityManager();
		Pessoa pessoa = manager.find(Pessoa.class, id);
		manager.close();
		
		return pessoa;
	}
	
	public void insert(Pessoa pessoa) throws PersistenceException{
		
		// Registrar pessoa na base	
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(pessoa);
		manager.getTransaction().commit();
		manager.close();
		Logger.getLogger(UsuarioDao.class.getName()).info("Insert Pessoa: [" + pessoa + ']');
	}
	
	public void update(Pessoa pessoa){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.merge(pessoa);		
		manager.getTransaction().commit();
		manager.close();	
	}
	
	
	public void delete(Pessoa pessoa){
		Logger.getLogger(PessoaDAO.class.getName()).info("delete pessoa " + pessoa);
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.remove(pessoa);
		manager.getTransaction().commit();
		manager.close();	
	}
	*/
	
	public PessoaFisica getFisicaByCPF(String cpf){
		EntityManager manager = JpaUtils.getEntityManager();
		String sql = "select a from PessoaFisica a where cpf = :cpf";
		List<PessoaFisica> results = manager.createQuery(sql, PessoaFisica.class)
									.setParameter("cpf", cpf)
									.getResultList();
		manager.close();
		
		Logger.getLogger(getClass()).info("Selecionar PF por CPF: [" + cpf + "]:"+!results.isEmpty());
		
		if (results.isEmpty()) { return null;}
		return results.get(0);
	}
	/*
	/**
	 * Listagem de todas as pessoas
	 * @return
	 
	public List<Pessoa> listaPessoas(){
		EntityManager manager = JPAUtil.getEntityManager();
		List<Pessoa> pessoas = manager.createQuery("select a from Pessoa a",Pessoa.class).getResultList();
		manager.close();
		
		return pessoas;
	}
		
	*/
}
