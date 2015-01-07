package com.rp.performance.domain;


public class RelatorioComparacao {

	private final float notaCandidato;
	private final float notaMaxima;
	private final float notaMinima;
	private final float notaMedia;
	private final float desvioPadrao;
	private final float variancia;

	private RelatorioComparacao(float notaCandidato, float notaMaxima,
			float notaMinima, float notaMedia, float desvioPadrao,
			float variancia) {
		this.notaCandidato = notaCandidato;
		this.notaMaxima = notaMaxima;
		this.notaMinima = notaMinima;
		this.notaMedia = notaMedia;
		this.desvioPadrao = desvioPadrao;
		this.variancia = variancia;
	}

	public float getNotaCandidato() {
		return notaCandidato;
	}

	public float getNotaMaxima() {
		return notaMaxima;
	}

	public float getNotaMinima() {
		return notaMinima;
	}

	public float getNotaMedia() {
		return notaMedia;
	}

	public float getDesvioPadrao() {
		return desvioPadrao;
	}

	public float getVariancia() {
		return variancia;
	}

	public static class RelatorioComparacaoBuilder {

		private float notaCandidato;
		private float notaMaxima;
		private float notaMinima;
		private float notaMedia;
		private float desvioPadrao;
		private float variancia;

		private RelatorioComparacaoBuilder() {

		}

		public static RelatorioComparacaoBuilder getInstance() {
			return new RelatorioComparacaoBuilder();
		}

		public RelatorioComparacaoBuilder notaCandidato(float nota) {
			this.notaCandidato = nota;
			return this;
		}

		public RelatorioComparacaoBuilder notaMaxima(float nota) {
			this.notaMaxima = nota;
			return this;
		}

		public RelatorioComparacaoBuilder notaMinima(float nota) {
			this.notaMinima = nota;
			return this;
		}

		public RelatorioComparacaoBuilder desvioPadrao(float desvioPadrao) {
			this.desvioPadrao = desvioPadrao;
			return this;
		}

		public RelatorioComparacaoBuilder notaMedia(float nota) {
			this.notaMedia = nota;
			return this;
		}

		public RelatorioComparacaoBuilder variancia(float nota) {
			this.variancia = nota;
			return this;
		}

		public RelatorioComparacao build() {
			return new RelatorioComparacao(notaCandidato, notaMaxima,
					notaMinima, notaMedia, desvioPadrao, variancia);
		}

	}

}