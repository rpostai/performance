package com.rp.performance.domain.prova;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

import com.rp.performance.domain.BaseEntity;
import com.rp.performance.domain.DateUtils;

@Entity
@Table(name = "prova")
public class Prova extends BaseEntity {

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

	public Set<Questao> getQuestoes() {
		return questoes;
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
	

}
