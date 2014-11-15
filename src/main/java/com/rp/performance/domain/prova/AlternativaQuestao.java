package com.rp.performance.domain.prova;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rp.performance.domain.BaseEntity;

@Entity
@Table(name = "questao_alternativa")
public class AlternativaQuestao extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "questao_id")
	private Questao questao;

	@Column(name = "descricao")
	@Lob
	private String descricao;

	@ElementCollection
	@CollectionTable(name = "questao_alternativa_anexos")
	private Set<String> anexos;

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Set<String> getAnexos() {
		return anexos;
	}

	public void setAnexos(Set<String> anexos) {
		this.anexos = anexos;
	}

	@Override
	public String toString() {
		return "AlternativaQuestao [questao=" + questao + ", descricao="
				+ descricao + ", anexos=" + anexos + "]";
	}

}
