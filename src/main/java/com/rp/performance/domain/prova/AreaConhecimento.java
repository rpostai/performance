package com.rp.performance.domain.prova;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rp.performance.domain.BaseEntity;

@Entity
@Table(name = "area_conhecimento")
public class AreaConhecimento extends BaseEntity {

	@Column(name = "descricao", nullable = false, length = 100)
	private String descricao;

	@OneToMany
	@JoinTable(name = "area_conhecimento_assunto", joinColumns = @JoinColumn(name = "area_conhecimento_id"), inverseJoinColumns = @JoinColumn(name = "assunto_id"))
	private Set<Assunto> assuntos;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Set<Assunto> getAssuntos() {
		return assuntos;
	}

	public void setAssuntos(Set<Assunto> assuntos) {
		this.assuntos = assuntos;
	}

}
