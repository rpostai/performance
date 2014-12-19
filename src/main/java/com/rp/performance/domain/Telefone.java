package com.rp.performance.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Telefone implements Serializable {
	
	private static final long serialVersionUID = -2820972935269241843L;

	@Column(name="tipo_telefone")
	@Convert(converter=TipoTelefoneConverter.class)
	private TipoTelefone tipoTelefone;

	@Column(name = "ddd", length = 2)
	@NotNull
	private int ddd;

	@Column(name = "numero", length = 9)
	@NotNull
	private int numero;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ddd;
		result = prime * result + numero;
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
		Telefone other = (Telefone) obj;
		if (ddd != other.ddd)
			return false;
		if (numero != other.numero)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Telefone [ddd=" + ddd + ", numero=" + numero + "]";
	}

}
