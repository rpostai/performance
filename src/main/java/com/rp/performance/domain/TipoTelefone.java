package com.rp.performance.domain;

import java.util.Arrays;
import java.util.Optional;

public enum TipoTelefone {
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
