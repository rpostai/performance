package com.rp.performance.domain.prova;

import java.util.ArrayList;
import java.util.List;

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

	// @ElementCollection
	// @CollectionTable(name = "questao_alternativa_anexos", joinColumns =
	// @JoinColumn(name = "questao_alternativa_id"))
	// List<String> anexo = new ArrayList<>();

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((questao == null) ? 0 : questao.hashCode());
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
		AlternativaQuestao other = (AlternativaQuestao) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (questao == null) {
			if (other.questao != null)
				return false;
		} else if (!questao.equals(other.questao))
			return false;
		return true;
	}

	// public List<String> getAnexos() {
	// return anexo;
	// }
	//
	// public void addAnexo(String anexo) {
	// this.anexo.add(anexo);
	// }

}
