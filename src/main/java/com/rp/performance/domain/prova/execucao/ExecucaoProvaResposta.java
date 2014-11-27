package com.rp.performance.domain.prova.execucao;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rp.performance.domain.BaseEntity;
import com.rp.performance.domain.prova.AlternativaQuestao;
import com.rp.performance.domain.prova.Questao;

@Entity
@Table(name = "execucao_prova_resposta")
public class ExecucaoProvaResposta extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "execucao_prova_id")
	private ExecucaoProva execucaoProva;

	@ManyToOne
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

	public void setRespostaAberta(String respostaAberta) {
		this.respostaAberta = respostaAberta;
	}

}
