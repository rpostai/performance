package com.rp.performance.domain.prova;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rp.performance.domain.BaseEntity;

@Entity
@Table(name = "questao")
public class Questao extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "area_conhecimento_id", nullable = false)
	private AreaConhecimento areaConhecimento;

	@ManyToMany
	@JoinTable(name = "questao_assunto", joinColumns = @JoinColumn(name = "questao_id"), inverseJoinColumns = @JoinColumn(name = "assunto_id"))
	private Set<Assunto> assuntos = new HashSet<Assunto>();

	@ManyToOne
	@JoinColumn(name = "nivel_dificuldade_id", nullable = true)
	private NivelDificuldade nivelDificuldade;

	@Convert(attributeName = "tipo_questao", converter = TipoQuestaoConverter.class)
	@Column(name = "tipo_questao", length = 1, nullable = false)
	private TipoQuestao tipoQuestao;

	@Lob
	@Column(name = "questao", nullable = false)
	private String questao;

	@ElementCollection
	@CollectionTable(name = "questao_anexos", joinColumns = @JoinColumn(name = "questao_id"))
	private List<String> anexo = new ArrayList<>();

	@OneToMany(mappedBy = "questao", cascade = CascadeType.ALL)
	private List<AlternativaQuestao> alternativas = new ArrayList<AlternativaQuestao>();

	@OneToMany
	@JoinTable(name = "questao_gabarito", joinColumns = @JoinColumn(name = "questao_id"), inverseJoinColumns = @JoinColumn(name = "questao_alternativa_id"))
	private Set<AlternativaQuestao> gabarito = new HashSet<AlternativaQuestao>();

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

	public NivelDificuldade getNivelDificuldade() {
		return nivelDificuldade;
	}

	public void setNivelDificuldade(NivelDificuldade nivelDificuldade) {
		this.nivelDificuldade = nivelDificuldade;
	}

	public TipoQuestao getTipoQuestao() {
		return tipoQuestao;
	}

	public void setTipoQuestao(TipoQuestao tipoQuestao) {
		this.tipoQuestao = tipoQuestao;
	}

	public String getQuestao() {
		return questao;
	}

	public void setQuestao(String questao) {
		this.questao = questao;
	}

	public List<String> getAnexos() {
		return Collections.unmodifiableList(anexo);
	}

	public void addAnexo(String anexo) {
		this.anexo.add(anexo);
	}

	public List<AlternativaQuestao> getAlternativas() {
		return Collections.unmodifiableList(alternativas);
	}

	public void addAlternativa(AlternativaQuestao alternativa) {
		alternativas.add(alternativa);
	}

	public void removeAlternativa(AlternativaQuestao alternativa) {
		alternativas.removeIf(x -> {
			return x.getId().equals(alternativa.getId())
					|| x.getDescricao().equals(alternativa.getDescricao());
		});
	}

	public Set<AlternativaQuestao> getGabarito() {
		return gabarito;
	}

	public void addGabarito(AlternativaQuestao gabarito) {
		this.gabarito.add(gabarito);
	}

	public void removeGabarito(AlternativaQuestao alternativa) {
		gabarito.removeIf(x -> {
			return x.getId().equals(x.getId())
					|| x.getDescricao().equals(alternativa.getDescricao());
		});
	}

	public void addAssunto(Assunto assunto) {
		this.assuntos.add(assunto);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((alternativas == null) ? 0 : alternativas.hashCode());
		result = prime * result + ((anexo == null) ? 0 : anexo.hashCode());
		result = prime
				* result
				+ ((areaConhecimento == null) ? 0 : areaConhecimento.hashCode());
		result = prime * result
				+ ((assuntos == null) ? 0 : assuntos.hashCode());
		result = prime
				* result
				+ ((nivelDificuldade == null) ? 0 : nivelDificuldade.hashCode());
		result = prime * result + ((questao == null) ? 0 : questao.hashCode());
		result = prime * result
				+ ((tipoQuestao == null) ? 0 : tipoQuestao.hashCode());
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
		Questao other = (Questao) obj;
		if (alternativas == null) {
			if (other.alternativas != null)
				return false;
		} else if (!alternativas.equals(other.alternativas))
			return false;
		if (anexo == null) {
			if (other.anexo != null)
				return false;
		} else if (!anexo.equals(other.anexo))
			return false;
		if (areaConhecimento == null) {
			if (other.areaConhecimento != null)
				return false;
		} else if (!areaConhecimento.equals(other.areaConhecimento))
			return false;
		if (assuntos == null) {
			if (other.assuntos != null)
				return false;
		} else if (!assuntos.equals(other.assuntos))
			return false;
		if (nivelDificuldade == null) {
			if (other.nivelDificuldade != null)
				return false;
		} else if (!nivelDificuldade.equals(other.nivelDificuldade))
			return false;
		if (questao == null) {
			if (other.questao != null)
				return false;
		} else if (!questao.equals(other.questao))
			return false;
		if (tipoQuestao != other.tipoQuestao)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Questao [areaConhecimento=" + areaConhecimento + ", assuntos="
				+ assuntos + ", nivelDificuldade=" + nivelDificuldade
				+ ", tipoQuestao=" + tipoQuestao + ", questao=" + questao
				+ ", anexos=" + anexo + ", alternativas=" + alternativas + "]";
	}

}
