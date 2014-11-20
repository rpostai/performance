package com.rp.performance.domain.prova;

import java.util.Arrays;
import java.util.Optional;

public enum TipoQuestao {
	MULTIPLA_ESCOLHA("M"), ESCOLHA_UNICA("U"), SOMATORIA("S"), ABERTA("A");

	private final String sigla;

	private TipoQuestao(String sigla) {
		this.sigla = sigla;
	}

	public String getSigla() {
		return sigla;
	}

	public static TipoQuestao getTipoQuestao(String sigla) {
		Optional<TipoQuestao> t = Arrays.asList(values()).stream()
				.filter(tipo -> {
					return tipo.getSigla().equals(sigla);
				}).findAny();
		return t.isPresent() ? t.get() : null;
	}

}
