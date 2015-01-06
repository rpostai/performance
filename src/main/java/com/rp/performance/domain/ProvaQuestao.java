package com.rp.performance.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "prova_questao")
public class ProvaQuestao extends BaseEntity {

	private static final long serialVersionUID = -5189033330519898205L;

	@Column(name = "ordem")
	private int ordem;

	@Column(name = "peso")
	private int peso;

	@ManyToOne
	@JoinColumn(name = "prova_id")
	private Prova prova;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "questao_id")
	private Questao questao;

	public ProvaQuestao() {
		this.ordem = 0;
		this.peso = 1;
	}

	public ProvaQuestao(int ordem, int peso, Questao questao) {
		this();
		this.ordem = ordem;
		this.peso = peso;
		this.questao = questao;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public Prova getProva() {
		return prova;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ordem;
		result = prime * result + peso;
		result = prime * result + ((prova == null) ? 0 : prova.hashCode());
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
		ProvaQuestao other = (ProvaQuestao) obj;
		if (ordem != other.ordem)
			return false;
		if (peso != other.peso)
			return false;
		if (prova == null) {
			if (other.prova != null)
				return false;
		} else if (!prova.equals(other.prova))
			return false;
		if (questao == null) {
			if (other.questao != null)
				return false;
		} else if (!questao.equals(other.questao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProvaQuestao [ordem=" + ordem + ", peso=" + peso + ", prova="
				+ prova + ", questao=" + questao + "]";
	}
	
	

}
