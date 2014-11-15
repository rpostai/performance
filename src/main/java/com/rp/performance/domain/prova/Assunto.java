package com.rp.performance.domain.prova;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rp.performance.domain.BaseEntity;

@Entity
@Table(name = "assunto")
public class Assunto extends BaseEntity {

	@Column(name = "descricao", length = 100, nullable = false)
	@NotNull
	@Size(min = 2, max = 100)
	private String assunto;

	@ManyToOne
	@JoinColumn(name = "assunto_pai_id")
	private Assunto assuntoPai;

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public Assunto getAssuntoPai() {
		return assuntoPai;
	}

	public void setAssuntoPai(Assunto assuntoPai) {
		this.assuntoPai = assuntoPai;
	}

}
