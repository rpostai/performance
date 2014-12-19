package com.rp.performance.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

import javax.persistence.Embeddable;

public enum TipoTelefone implements Serializable {
	FIXO("F"), CELULAR("C"), RADIO("R");

	private final String sigla;

	private TipoTelefone(String sigla) {
		this.sigla = sigla;
	}

	public String getSigla() {
		return sigla;
	}

	public static Optional<TipoTelefone> getTipoTelefone(String sigla) {
		return Arrays.asList(values()).stream().filter(tipo -> {
			return tipo.sigla.equals(sigla);
		}).findFirst();
	}

}
