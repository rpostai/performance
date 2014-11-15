package com.rp.performance.domain.prova;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.rp.performance.domain.BaseEntity;

@Entity
@Table(name = "prova")
public class Prova extends BaseEntity {

	@ManyToMany
	@JoinTable(name = "prova_questao", joinColumns = @JoinColumn(name = "prova_id"), inverseJoinColumns = @JoinColumn(name = "questao_id"))
	private Set<Questao> questoes;

}
