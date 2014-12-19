package com.rp.performance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "questao_alternativa")
public class AlternativaQuestao extends BaseEntity {

	private static final long serialVersionUID = 616271021734789785L;
	
	@ManyToOne
	@JoinColumn(name="questao_id")
	private Questao questao;

	@Column(name = "descricao")
	@Lob
	private String descricao;

	// @ElementCollection
	// @CollectionTable(name = "questao_alternativa_anexos", joinColumns =
	// @JoinColumn(name = "questao_alternativa_id"))
	// List<String> anexo = new ArrayList<>();

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AlternativaQuestao) {
			AlternativaQuestao q = (AlternativaQuestao) obj;
			return new EqualsBuilder().append(q.getId(), this.getId())
					.isEquals();
		}
		return false;
	}

	// public List<String> getAnexos() {
	// return anexo;
	// }
	//
	// public void addAnexo(String anexo) {
	// this.anexo.add(anexo);
	// }

}
