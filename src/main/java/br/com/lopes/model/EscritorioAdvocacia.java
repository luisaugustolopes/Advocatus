package br.com.lopes.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class EscritorioAdvocacia {
 
	@SequenceGenerator(name="escritorioAdvocaciaGenerator",sequenceName="seq_escritorioAdvocacia")
	@Id @GeneratedValue(generator="escritorioAdvocaciaGenerator")
	protected long id;
	
	@OneToMany(mappedBy="escritorio")	
	private List<Advogado> advogados;
	 
	public void produzProcesso() {
	}
	 
}
 
