package br.com.lopes.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.lopes.model.Cidade;
import br.com.lopes.util.JpaUtils;

public class CidadeDAO extends GenericDAO<Long, Cidade>{

	private EntityManager entityManager;

	public List<Cidade> getCidadesPorEstado(String estado){
		
		entityManager = JpaUtils.getEntityManager();
        
		@SuppressWarnings("unchecked")
		List<Cidade> cidades = entityManager.createQuery("select c FROM Cidade c WHERE c.estado = :estado")
        	.setParameter("estado",estado)
        	.getResultList();
		
		return cidades;
	}
}
