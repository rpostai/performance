package com.rp.performance.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "execucao_prova_resposta")
public class ExecucaoProvaResposta extends BaseEntity {

	private static final long serialVersionUID = -6008440178581238842L;

	@ManyToOne
	@JoinColumn(name = "execucao_prova_id")
	private ExecucaoProva execucaoProva;

	@OneToOne
	@JoinColumn(name = "questao_id")
	private Questao questao;

	@OneToMany
	@JoinTable(name = "execucao_prova_respostas_gabarito", joinColumns = @JoinColumn(name = "execucao_prova_resposta_id"), inverseJoinColumns = @JoinColumn(name = "alternativa_questao_id"))
	private List<AlternativaQuestao> respostas;

	@Lob
	@Column(name = "resposta_aberta")
	private String respostaAberta;

	public ExecucaoProva getExecucaoProva() {
		return execucaoProva;
	}

	public void setExecucaoProva(ExecucaoProva execucaoProva) {
		this.execucaoProva = execucaoProva;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public List<AlternativaQuestao> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<AlternativaQuestao> respostas) {
		this.respostas = respostas;
	}

	public String getRespostaAberta() {
		return respostaAberta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((execucaoProva == null) ? 0 : execucaoProva.hashCode());
		result = prime * result + ((questao == null) ? 0 : questao.hashCode());
		result = prime * result
				+ ((respostaAberta == null) ? 0 : respostaAberta.hashCode());
		result = prime * result
				+ ((respostas == null) ? 0 : respostas.hashCode());
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
		ExecucaoProvaResposta other = (ExecucaoProvaResposta) obj;
		if (execucaoProva == null) {
			if (other.execucaoProva != null)
				return false;
		} else if (!execucaoProva.equals(other.execucaoProva))
			return false;
		if (questao == null) {
			if (other.questao != null)
				return false;
		} else if (!questao.equals(other.questao))
			return false;
		if (respostaAberta == null) {
			if (other.respostaAberta != null)
				return false;
		} else if (!respostaAberta.equals(other.respostaAberta))
			return false;
		if (respostas == null) {
			if (other.respostas != null)
				return false;
		} else if (!respostas.equals(other.respostas))
			return false;
		return true;
	}

	public void setRespostaAberta(String respostaAberta) {
		this.respostaAberta = respostaAberta;
	}

}
