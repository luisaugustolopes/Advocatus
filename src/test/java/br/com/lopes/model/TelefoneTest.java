package br.com.lopes.model;

import br.com.lopes.dao.TelefoneDAO;

public class TelefoneTest {

	
	public static void main(String[] args) {
		
		TelefoneDAO dao = new TelefoneDAO();
		Telefone telefone = dao.getById(Long.parseLong("24"));
		
		//dao.delete(telefone);		
	}
}
