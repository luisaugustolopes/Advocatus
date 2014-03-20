package br.com.lopes.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="Jurídica")
//@Table(uniqueConstraints=@UniqueConstraint(columnNames={"cnpj"}))
//@Inheritance(strategy=InheritanceType.JOINED)
//@PrimaryKeyJoinColumn(name="id_pessoajuridica")
public class PessoaJuridica extends Pessoa {
 
	@Column
	private String cnpj;
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	
	@Override
	public String toString() {
		return super.toString() + " CNPJ: " + cnpj;
	}
}
 
