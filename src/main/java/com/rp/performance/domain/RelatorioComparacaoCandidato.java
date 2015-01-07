package com.rp.performance.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.rp.performance.domain.RelatorioCorrecaoProva.Relatorio;
import com.rp.performance.domain.utils.Estatistica;

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

	public List<RelatorioComparacao> gerarRelatoroio() {

		List<RelatorioComparacao> result = new ArrayList<RelatorioComparacao>();

		RelatorioCorrecaoProva principal = new RelatorioCorrecaoProva(
				provaBase.corrigirProva());

		Stream<Float> notas = provas
				.parallelStream()
				.map(prova -> {
					RelatorioCorrecaoProva relatorioCorrecaoProva = new RelatorioCorrecaoProva(
							prova.corrigirProva());
					return relatorioCorrecaoProva.getNotaFinal();
				});

		result.add(gerarRelatorio(principal.getNotaFinal(), notas));
		
		Stream<RelatorioCorrecaoProva> relatorios = provas.parallelStream()
				.map(prova -> {
					return new RelatorioCorrecaoProva(prova.corrigirProva());
				});
		
		Map<Assunto, RelatorioComparacao> relatoriosAssunto = gerarRelatorioNotasPorAssunto(principal, relatorios);


		return result;

	}
	
	private Map<Assunto, RelatorioComparacao> gerarRelatorioNotasPorAssunto(RelatorioCorrecaoProva principal, Stream<RelatorioCorrecaoProva> relatorios) {
		
		Map<Assunto, RelatorioComparacao> result = new ConcurrentHashMap<>();
		
		List<Relatorio> relatoriosAssuntoPrincipal = principal.getNotasPorAssunto();
		
		relatoriosAssuntoPrincipal.parallelStream().forEach(rel -> {

			float notaCandidato = rel.getNotaFinal();

			Assunto assunto = (Assunto) rel.getAgrupador();
			
			Stream<Float> notasAssuntoTodasProvas = relatorios.map(relatorio -> {
				Optional<Relatorio> relatorioAssunto = relatorio.getNotasPorAssunto().stream().filter(nota -> {
					return assunto.equals(nota.getAgrupador());
				}).findFirst();
				
				return relatorioAssunto.get().getNotaFinal();
			});
			
			RelatorioComparacao r = gerarRelatorio(notaCandidato, notasAssuntoTodasProvas);
			
			result.put(assunto, r);
		});
		
		return result;
	}

	private RelatorioComparacao gerarRelatorio(Float notaFinal,
			Stream<Float> notas) {
		List<Float> notasList = notas.collect(Collectors.toList());
		float notaMinima =  Estatistica.calcularValorMinimo(notasList);
		float notaMaxima = Estatistica.calcularValorMaximo(notasList);
		float notaMedia = Estatistica.calcularMedia(notasList);
		float variancia = Estatistica.calcularVariancia(notaMedia, notasList);
		float desvioPadrao = Estatistica.calcularDesvioPadrao(notaMedia,
				notasList);

		RelatorioComparacao.RelatorioComparacaoBuilder builder = RelatorioComparacao.RelatorioComparacaoBuilder
				.getInstance();
		return builder.notaCandidato(notaFinal).notaMaxima(notaMaxima)
				.notaMedia(notaMedia).desvioPadrao(desvioPadrao)
				.notaMinima(notaMinima).variancia(variancia).build();
	}

}
