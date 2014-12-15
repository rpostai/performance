package com.rp.performance.domain.prova.execucao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rp.performance.domain.BaseEntity;
import com.rp.performance.domain.prova.AlternativaQuestao;
import com.rp.performance.domain.prova.AreaConhecimento;
import com.rp.performance.domain.prova.Assunto;
import com.rp.performance.domain.prova.NivelDificuldade;
import com.rp.performance.domain.prova.Questao;
import com.rp.performance.domain.prova.TipoQuestao;
import com.rp.performance.domain.prova.TipoQuestaoConverter;

@Entity
@Table(name = "correcao_prova")
public class CorrecaoProva extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "execucao_prova_id")
	private ExecucaoProva execucao;

	@ManyToOne
	@JoinColumn(name = "area_conhecimento_id", nullable = false)
	private AreaConhecimento areaConhecimento;

	@ManyToMany
	@JoinTable(name = "correcao_prova_assunto", joinColumns = @JoinColumn(name = "correcao_prova_id"), inverseJoinColumns = @JoinColumn(name = "assunto_id"))
	private Set<Assunto> assuntos = new HashSet<Assunto>();

	@Convert(attributeName = "tipo_questao", converter = TipoQuestaoConverter.class)
	@Column(name = "tipo_questao", length = 1, nullable = false)
	private TipoQuestao tipoQuestao;

	@ManyToOne
	@JoinColumn(name = "nivel_dificuldade_id", nullable = true)
	private NivelDificuldade nivelDificuldade;

	@ManyToOne
	@JoinColumn(name = "questao_id")
	private Questao questao;

	@OneToMany
	@JoinTable(name = "correcao_prova_gabarito", joinColumns = @JoinColumn(name = "correcao_prova_id"), inverseJoinColumns = @JoinColumn(name = "alternativa_questao_id"))
	private List<AlternativaQuestao> gabarito;

	@OneToMany
	@JoinTable(name = "correcao_prova_resposta", joinColumns = @JoinColumn(name = "correcao_prova_id"), inverseJoinColumns = @JoinColumn(name = "alternativa_questao_id"))
	private List<AlternativaQuestao> respostas;

	@Column(name = "resposta_aberta")
	@Lob
	private String respostaAberta;

	@Column(name = "nota_calculada")
	private Float notaCalculada;

	@Column(name = "nota_manual")
	private Float notaManual;

	private boolean questaoCorreta;

	private boolean questaoParcialmentecorreta;

	public CorrecaoProva(ExecucaoProva execucao, Questao questao) {
		this.execucao = execucao;
		this.questao = questao;
		if (this.execucao == null) {
			throw new IllegalArgumentException(
					"Prova executada não pode ser nula");
		}
		if (this.questao == null) {
			throw new IllegalArgumentException("Questão não pode ser nula");
		}
	}

	public ExecucaoProva getExecucao() {
		return execucao;
	}

	public void setExecucao(ExecucaoProva execucao) {
		this.execucao = execucao;
	}

	public AreaConhecimento getAreaConhecimento() {
		return areaConhecimento;
	}

	public void setAreaConhecimento(AreaConhecimento areaConhecimento) {
		this.areaConhecimento = areaConhecimento;
	}

	public Set<Assunto> getAssuntos() {
		return assuntos;
	}

	public void setAssuntos(Set<Assunto> assuntos) {
		this.assuntos = assuntos;
	}

	public TipoQuestao getTipoQuestao() {
		return tipoQuestao;
	}

	public void setTipoQuestao(TipoQuestao tipoQuestao) {
		this.tipoQuestao = tipoQuestao;
	}

	public NivelDificuldade getNivelDificuldade() {
		return nivelDificuldade;
	}

	public void setNivelDificuldade(NivelDificuldade nivelDificuldade) {
		this.nivelDificuldade = nivelDificuldade;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public List<AlternativaQuestao> getGabarito() {
		return gabarito;
	}

	public void setGabarito(List<AlternativaQuestao> gabarito) {
		this.gabarito = gabarito;
	}

	public List<AlternativaQuestao> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<AlternativaQuestao> respostas) {
		this.respostas = respostas;
	}

	public Float getNotaCalculada() {
		return notaCalculada;
	}

	public Float getNotaManual() {
		return notaManual;
	}

	public void setNotaManual(Float notaManual) {
		this.notaManual = notaManual;
	}

	public void setNotaCalculada(Float notaCalculada) {
		this.notaCalculada = notaCalculada;
	}

	public String getRespostaAberta() {
		return respostaAberta;
	}

	public void setRespostaAberta(String respostaAberta) {
		this.respostaAberta = respostaAberta;
	}

	public boolean isQuestaoCorreta() {
		return questaoCorreta;
	}

	public void setQuestaoCorreta(boolean questaoCorreta) {
		this.questaoCorreta = questaoCorreta;
	}

	public boolean isQuestaoParcialmentecorreta() {
		return questaoParcialmentecorreta;
	}

	public void setQuestaoParcialmentecorreta(boolean questaoParcialmentecorreta) {
		this.questaoParcialmentecorreta = questaoParcialmentecorreta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((execucao == null) ? 0 : execucao.hashCode());
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
		CorrecaoProva other = (CorrecaoProva) obj;
		if (execucao == null) {
			if (other.execucao != null)
				return false;
		} else if (!execucao.equals(other.execucao))
			return false;
		if (questao == null) {
			if (other.questao != null)
				return false;
		} else if (!questao.equals(other.questao))
			return false;
		return true;
	}

}
