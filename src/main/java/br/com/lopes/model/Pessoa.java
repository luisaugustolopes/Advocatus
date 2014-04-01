package br.com.lopes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TIPO")
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"cpf"},name="UK_PESSOA_FISICA"),
						  @UniqueConstraint(columnNames={"cnpj"},name="UK_PESSOA_JURIDICA")})										
public abstract class Pessoa {
 
	@SequenceGenerator(name="pessoaGenerator",sequenceName="seq_pessoa")
	@Id @GeneratedValue(generator="pessoaGenerator")
	protected long id;
	
	@Column(nullable=false)
	@NotNull(message="Informe o Nome")
	protected String nome;
	
	@Column
	protected String email;
	
	@Column(insertable=false)
	private String tipo;
	
	@ForeignKey(name="FK_PESSOA_USUARIO")
	@OneToOne
	protected Usuario usuario;
	
	@OneToOne(mappedBy="pessoa",cascade=CascadeType.ALL)
	protected Endereco endereco;
	
	@OneToMany(mappedBy="pessoa",cascade=CascadeType.REMOVE)
	private List<Telefone> telefones;	
	
	public Pessoa(){}
	
	public Pessoa(String nome){
		this.nome = nome;
	}	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Telefone> getTelefones() {		
		return telefones;
	}

	public void setTelefones(List<Telefone> telefone) {
		this.telefones = telefone;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString(){
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
 
