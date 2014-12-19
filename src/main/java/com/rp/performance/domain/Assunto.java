package com.rp.performance.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "assunto")
public class Assunto extends BaseEntity {

	@Column(name = "descricao", length = 100, nullable = false)
	@NotNull
	@Size(min = 2, max = 100)
	private String assunto;

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name = "assunto_assunto", joinColumns = @JoinColumn(name = "assunto_id"), inverseJoinColumns = @JoinColumn(name = "assunto_filho_id"))
	private Set<Assunto> assuntos = new HashSet<Assunto>();

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public Set<Assunto> getAssuntos() {
		return Collections.unmodifiableSet(assuntos);
	}

	public void addAssunto(Assunto assunto) {
		assuntos.add(assunto);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
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
		Assunto other = (Assunto) obj;
		if (assunto == null) {
			if (other.assunto != null)
				return false;
		} else if (!assunto.equals(other.assunto))
			return false;
		return true;
	}

}
