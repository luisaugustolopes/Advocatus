package br.com.lopes.selenium;

import javax.persistence.EntityManager;
import org.junit.Test;

import br.com.lopes.model.PessoaFisica;
import br.com.lopes.util.JpaUtils;


public class InsertPessoaTestCase{

	
	public void inserirPessoaFisica(){
		//PessoaBean bean = new PessoaBean();
		PessoaFisica fisica = new PessoaFisica();
		//PessoaDAO dao = new PessoaDAO();
		
		fisica.setCpf("222.222.222-22");
		fisica.setNome("TESTE DE INSERÇÃO");
		fisica.setSexo('M');
		
		//bean.setFisica(fisica);
		
		EntityManager manager = JpaUtils.getEntityManager();
		
		try{
			manager.getTransaction().begin();
			manager.persist(fisica);
			manager.getTransaction().commit();
			//dao.save(fisica);
			//bean.save(fisica);
		}catch (RuntimeException e){
			System.out.println(e.getMessage());
		}
		
		//Save after error
		fisica.setCpf("555.555.555-55");
		fisica.setNome("TESTE DE INSERÇÃO 2");
		
		System.out.println(fisica.getCpf() + "#" + fisica.getId());
		
		//bean.save(fisica);
		//dao.save(fisica);
		manager.merge(fisica);
		manager.getTransaction().commit();
	}
}
