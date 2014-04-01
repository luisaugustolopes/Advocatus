package br.com.lopes.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"pessoa_id"},name="UK_ENDERECO_PESSOA")})
public class Endereco {
 
	@SequenceGenerator(name="enderecoGenerator",sequenceName="seq_endereco")
	@Id @GeneratedValue(generator="enderecoGenerator")
	private long id;	
	
	@Column(nullable=false)
	@Index(name="AK_ENDERECO_ENDERECO")
	private String endereco;
	private String cep;
	private int numero;
	private String complemento;
	
	@Index(name="AK_ENDERECO_PESSOA")
	@ForeignKey(name="FK_ENDERECO_PESSOA")
	@OneToOne(optional=false)
	private Pessoa pessoa;
	
	@ForeignKey(name="FK_ENDERECO_CIDADE")
	@ManyToOne(optional=false)
	private Cidade cidade;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	
	@Override
	public String toString() {
		return endereco + '-' + cidade.getNome() + '/' + cidade.getEstado();
	}
}
 
