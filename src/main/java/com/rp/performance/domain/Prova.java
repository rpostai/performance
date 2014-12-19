package com.rp.performance.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
	@JoinColumn(name = "area_conhecimento_id", nullable=false)
	private AreaConhecimento areaConhecimento;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "prova_questao", joinColumns = @JoinColumn(name = "prova_id"), inverseJoinColumns = @JoinColumn(name = "questao_id"))
	private Set<Questao> questoes = new HashSet<Questao>();

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Questao> getQuestoes() {
		return questoes.stream().collect(Collectors.toList());
	}

	public void addQuestao(Questao questao) {
		this.questoes.add(questao);
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
		
		questoes.stream().parallel().forEach(questao -> {
			Questao q = new Questao();
			q.setQuestao(questao.getQuestao());
			q.setEmpresa(questao.getEmpresa());
			q.setNivelDificuldade(questao.getNivelDificuldade());
			q.setTipoQuestao(questao.getTipoQuestao());
			q.setAreaConhecimento(questao.getAreaConhecimento());
			
			questao.getAlternativas().stream().parallel().forEach(alternativa -> {
				AlternativaQuestao a = new AlternativaQuestao();
				a.setDescricao(alternativa.getDescricao());
				a.setEmpresa(a.getEmpresa());
				q.addAlternativa(a);
			});
			
			questao.getGabarito().stream().parallel().forEach(gabarito -> {
				AlternativaQuestao g = new AlternativaQuestao();
				g.setDescricao(gabarito.getDescricao());
				g.setEmpresa(gabarito.getEmpresa());
				q.addGabarito(g);
			});
			
			questao.getAssuntos().stream().parallel().forEach(assunto -> {
				q.addAssunto(assunto);
			});
			
			prova.addQuestao(q);
		});
		return prova;
	}
}
