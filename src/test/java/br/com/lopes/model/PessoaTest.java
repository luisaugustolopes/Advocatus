package br.com.lopes.model;

import br.com.lopes.controller.PessoaBean;

public class PessoaTest {
	
	public static void main(String[] args) {
		
		PessoaFisica fisica = new PessoaFisica();
		
		fisica.setCpf("12345678901");
		fisica.setNome("João");
		
		
		PessoaBean pessoaBean = new PessoaBean();
		//PessoaFisica pessoaFisica = (PessoaFisica) pessoaBean.getPessoa(8);
		
		
		//pessoaBean.gravarPessoa(fisica);
		
		/*Advogado advogado = new Advogado();
		advogado.setNumeroOAB("321654987");
		pessoaFisica.setAdvogado(advogado);
		*/
		//pessoaBean.salvarPessoa(advogado);
				
/*		
		Advogado advogado = new Advogado();
		advogado.setCpf("123456789");
		advogado.setNome("PAULO");
		
		pessoaBean.gravarPessoa(advogado);
*/	}
}
