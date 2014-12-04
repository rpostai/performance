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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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

	@OneToMany(cascade=CascadeType.ALL)
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
		gabarito.setQuestao(this);
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
		return new HashCodeBuilder().append(tipoQuestao)
				.append(nivelDificuldade).append(questao).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Questao) {
			Questao q = (Questao) obj;
			return new EqualsBuilder()
					.append(q.getTipoQuestao(), this.getTipoQuestao())
					.append(q.getNivelDificuldade(), this.getNivelDificuldade())
					.append(q.getQuestao(), this.getQuestao()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return "Questao [areaConhecimento=" + areaConhecimento + ", assuntos="
				+ assuntos + ", nivelDificuldade=" + nivelDificuldade
				+ ", tipoQuestao=" + tipoQuestao + ", questao=" + questao
				+ ", anexos=" + anexo + ", alternativas=" + alternativas + "]";
	}

}
