package com.rp.performance.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "prova")
public class Prova extends BaseEntity {

	private static final long serialVersionUID = 8800743897823648900L;

	@Column(name = "descricao", nullable = false, length = 100)
	private String descricao;

	@Column(name = "tempo_duracao", nullable = false, length = 3)
	@Min(value = 1)
	@Max(value = 999)
	private int tempoDuracaoEmMinutos;

	@Lob
	@Column(name = "orientacoes", nullable = true)
	private String orientacoes;

	@ManyToOne
	@JoinColumn(name = "area_conhecimento_id", nullable = false)
	private AreaConhecimento areaConhecimento;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "prova")
	private Set<ProvaQuestao> questoes = new LinkedHashSet<ProvaQuestao>(); // TreeSet<ProvaQuestao>();

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<ProvaQuestao> getQuestoes() {
		return questoes.stream().collect(Collectors.toList());
	}

	public void addQuestao(ProvaQuestao questao) {
		this.questoes.add(questao);
		questao.setProva(this);
	}

	public int getTempoDuracaoEmMinutos() {
		return tempoDuracaoEmMinutos;
	}

	public void setTempoDuracaoEmMinutos(int tempoDuracaoEmMinutos) {
		this.tempoDuracaoEmMinutos = tempoDuracaoEmMinutos;
	}

	public String getOrientacoes() {
		return orientacoes;
	}

	public void setOrientacoes(String orientacoes) {
		this.orientacoes = orientacoes;
	}

	public AreaConhecimento getAreaConhecimento() {
		return areaConhecimento;
	}

	public void setAreaConhecimento(AreaConhecimento areaConhecimento) {
		this.areaConhecimento = areaConhecimento;
	}

	public boolean isTempoLimiteValido(Date dataInicio) {
		Calendar agora = DateUtils.getCalendar();

		Calendar dataFinalPrevista = DateUtils.getCalendar();
		dataFinalPrevista.setTimeInMillis(dataInicio.getTime());

		dataFinalPrevista.add(Calendar.MINUTE, getTempoDuracaoEmMinutos());

		return dataFinalPrevista.compareTo(agora) > 0;
	}

	public Prova criarVersaoProva() {

		Prova prova = new Prova();
		prova.setDescricao(this.getDescricao());
		prova.setEmpresa(this.getEmpresa());
		prova.setOrientacoes(this.getOrientacoes());
		prova.setTempoDuracaoEmMinutos(this.getTempoDuracaoEmMinutos());
		prova.setAreaConhecimento(this.getAreaConhecimento());

		questoes.stream()
				.parallel()
				.forEach(
						questao -> {

							Questao questaoInterna = questao.getQuestao();

							Questao q = new Questao();
							q.setQuestao(questaoInterna.getQuestao());
							q.setEmpresa(questaoInterna.getEmpresa());
							q.setNivelDificuldade(questaoInterna
									.getNivelDificuldade());
							q.setTipoQuestao(questaoInterna.getTipoQuestao());
							q.setAreaConhecimento(questaoInterna
									.getAreaConhecimento());

							questaoInterna
									.getAlternativas()
									.stream()
									.parallel()
									.forEach(
											alternativa -> {
												AlternativaQuestao a = new AlternativaQuestao();
												a.setDescricao(alternativa
														.getDescricao());
												a.setEmpresa(a.getEmpresa());
												q.addAlternativa(a);
											});

							questaoInterna
									.getGabarito()
									.stream()
									.parallel()
									.forEach(
											gabarito -> {
												AlternativaQuestao g = new AlternativaQuestao();
												g.setDescricao(gabarito
														.getDescricao());
												g.setEmpresa(gabarito
														.getEmpresa());
												q.addGabarito(g);
											});

							questaoInterna.getAssuntos().stream().parallel()
									.forEach(assunto -> {
										q.addAssunto(assunto);
									});

							ProvaQuestao pq = new ProvaQuestao();
							pq.setQuestao(q);
							pq.setOrdem(questao.getOrdem());
							pq.setPeso(pq.getPeso());
							prova.addQuestao(pq);
						});
		return prova;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((areaConhecimento == null) ? 0 : areaConhecimento.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((orientacoes == null) ? 0 : orientacoes.hashCode());
		result = prime * result + tempoDuracaoEmMinutos;
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
		Prova other = (Prova) obj;
		if (areaConhecimento == null) {
			if (other.areaConhecimento != null)
				return false;
		} else if (!areaConhecimento.equals(other.areaConhecimento))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (orientacoes == null) {
			if (other.orientacoes != null)
				return false;
		} else if (!orientacoes.equals(other.orientacoes))
			return false;
		if (tempoDuracaoEmMinutos != other.tempoDuracaoEmMinutos)
			return false;
		return true;
	}
	

}
