package com.rp.performance.domain.prova;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name = "area_conhecimento_assunto", joinColumns = @JoinColumn(name = "area_conhecimento_id"), inverseJoinColumns = @JoinColumn(name = "assunto_id"))
	private Set<Assunto> assuntos = new HashSet<Assunto>();

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Set<Assunto> getAssuntos() {
		return Collections.unmodifiableSet(assuntos);
	}
	
	public void addAssunto(Assunto assunto) {
		assuntos.add(assunto);
	}

}
