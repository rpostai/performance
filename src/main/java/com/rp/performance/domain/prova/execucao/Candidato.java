package com.rp.performance.domain.prova.execucao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rp.performance.domain.BaseEntity;

@Entity
@Table(name = "candidato", uniqueConstraints = { @UniqueConstraint(columnNames = { "cpf" }) })
public class Candidato extends BaseEntity {

	@Column(name = "nome", length = 200, nullable = false)
	@NotNull
	@Size(min = 2, max = 200)
	private String nome;

	@NotNull
	@Column(name = "cpf", length = 11, nullable = false)
	@Size(min = 11, max = 11)
	private String cpf;
	
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
		Candidato other = (Candidato) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}

}
