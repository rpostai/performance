package com.rp.performance.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RelatorioComparacaoCandidato {

	private ExecucaoProva provaBase;

	private List<ExecucaoProva> provas = new ArrayList<ExecucaoProva>();

	private RelatorioComparacaoCandidato(ExecucaoProva prova,
			ExecucaoProva provaComparar) {
		provaBase = prova;
		provas.add(provaComparar);
	}

	public void adicionaProvaComparacao(ExecucaoProva novaProvaComparacao) {
		provas.add(novaProvaComparacao);
	}

	public void gerarRelatorio() {
		RelatorioCorrecaoProva principal = new RelatorioCorrecaoProva(
				provaBase.corrigirProva());

		Stream<Float> notas = provas.parallelStream()
				.map(prova -> {
					RelatorioCorrecaoProva relatorioCorrecaoProva = new RelatorioCorrecaoProva(
							prova.corrigirProva());
					return relatorioCorrecaoProva.getNotaFinal();
				});
		
		float notaMinima = notas.min((x,y) -> {
			return x > y ? 1 : x < y ? -1 : 0;
		}).get();
		
		float notaMaxima = notas.max((x,y) -> {
			return x > y ? 1 : x < y ? -1 : 0;
		}).get();
		
		float notaMedia = notas.reduce((atual, accumulator) -> {
			return atual + accumulator / 2;
		}).get();
		
	}

	public class RelatorioComparacao {
		
		private float notaCandidato;
		private float notaMaxima;
		private float notaMinima;
		private float notaMedia;
		private float desvioPadrao;
		private float variancia;
	}

}
