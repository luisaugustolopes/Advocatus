package br.com.lopes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;


@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"numeroOAB"},name="UK_ADVOGADO_NUMERO_OAB"),
						  @UniqueConstraint(columnNames={"fisica_id"},name="UK_ADVOGADO_PESSOA")})
public class Advogado {
 
	@SequenceGenerator(name="advogadoGenerator",sequenceName="seq_advogado")
	@Id @GeneratedValue(generator="advogadoGenerator")
	protected long id;
	
	@Column(nullable=false)
	private String numeroOAB;
	
	@OneToOne(optional=false)	
	@ForeignKey(name="FK_ADVOGADO_PESSOA")
	private PessoaFisica fisica;
	
	@ManyToMany(mappedBy="advogados")
	private List<ProcessoJuridico> processosJuridicos;

	@ManyToOne
	@ForeignKey(name="FK_ADVOGADO_ESCRITORIO")
	private EscritorioAdvocacia escritorio;

	
	public String getNumeroOAB() {
		return numeroOAB;
	}

	public void setNumeroOAB(String numeroOAB) {
		this.numeroOAB = numeroOAB;
	}
	
	
	 
}
 
