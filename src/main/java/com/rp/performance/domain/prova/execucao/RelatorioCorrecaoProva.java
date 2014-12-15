package com.rp.performance.domain.prova.execucao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.rp.performance.domain.prova.AreaConhecimento;

public class RelatorioCorrecaoProva {

	private List<CorrecaoProva> items;

	public RelatorioCorrecaoProva(List<CorrecaoProva> items) {
		this.items = items;
		if (CollectionUtils.isEmpty(items)) {
			throw new IllegalArgumentException(
					"Prova não foi corrigida para ser gerado relatório");
		}
	}

	public float getNotaFinal() {
		Optional<Float> result = items.stream().parallel().map(correcao -> {
			return getNotaFinalItemCorrecao(correcao);
		}).reduce((atual, proximo) -> {
			return atual.floatValue() + proximo.floatValue();
		});
		return result.get().floatValue();
	}

	private float getNotaFinalItemCorrecao(CorrecaoProva correcao) {
		return correcao.getNotaManual() != null ? correcao.getNotaManual()
				: correcao.getNotaCalculada() != null ? correcao
						.getNotaCalculada() : new Float(0);
	}

	public List<RelatorioAreaConhecimento> getNotasPorAreaConhecimento() {
		
		List<RelatorioAreaConhecimento> resultado = new ArrayList<RelatorioCorrecaoProva.RelatorioAreaConhecimento>();
		
		List<AreaConhecimento> areasConhecimento = items.stream().map(correcao -> {
			return correcao.getAreaConhecimento();
		}).distinct().sorted((a1, a2) -> {
			return a1.getDescricao().compareTo(a2.getDescricao());
		}).collect(Collectors.toList());
		
		areasConhecimento.stream().forEachOrdered(areaConhecimento -> {
			
			RelatorioAreaConhecimento relatorio = new RelatorioAreaConhecimento();
			
			relatorio.setAreaConhecimento(areaConhecimento);
			
			List<CorrecaoProva> correcoesPorAreaConhecimento = items.stream().filter(correcao -> {
				return correcao.getAreaConhecimento().equals(areaConhecimento);
			}).collect(Collectors.toList());
			
			relatorio.setTotalQuestoes(correcoesPorAreaConhecimento.size());
			
			List<CorrecaoProva> questoesCorretas = getQuestoesCorretas(correcoesPorAreaConhecimento);
			relatorio.setTotalQuestoesCorretas(questoesCorretas.size());
			relatorio.setNotaFinalQuestoesCorretas(totalizarNota(questoesCorretas));
			
			List<CorrecaoProva> questoesParcialmenteCorretas = getQuestoesParcialmenteCorretas(correcoesPorAreaConhecimento);
			relatorio.setTotalQuestoesParcialmenteCorretas(questoesParcialmenteCorretas.size());
			relatorio.setNotaFinalQuestoesParcialmenteCorretas(totalizarNota(questoesParcialmenteCorretas));
			
			resultado.add(relatorio);
			
		});
		
		return resultado;
	}

	private Float totalizarNota(List<CorrecaoProva> listaCorrecoes) {
		Optional<Float> result = listaCorrecoes.stream().map(correcao -> {
			return	getNotaFinalItemCorrecao(correcao);
		}).reduce((atual, proximo) -> {
			return atual +  proximo;
		});
		if (result.isPresent()) {
			return result.get();
		} else {
			return new Float(0);
		}
	}

	private List<CorrecaoProva> getQuestoesCorretas(
			List<CorrecaoProva> listaCorrecoes) {
		return listaCorrecoes.stream().filter(correcao -> {
			return correcao.isQuestaoCorreta();
		}).collect(Collectors.toList());
	}

	private List<CorrecaoProva> getQuestoesParcialmenteCorretas(
			List<CorrecaoProva> listaCorrecoes) {
		return listaCorrecoes.stream().filter(correcao -> {
			return correcao.isQuestaoParcialmentecorreta();
		}).collect(Collectors.toList());
	}

	public class RelatorioAreaConhecimento {

		private AreaConhecimento areaConhecimento;

		private float totalQuestoes;

		private float totalQuestoesCorretas;

		private float totalQuestoesParcialmenteCorretas;

		private float notaFinalQuestoesCorretas;

		private float notaFinalQuestoesParcialmenteCorretas;

		public float getTotalQuestoesParcialmenteCorretas() {
			return totalQuestoesParcialmenteCorretas;
		}

		public void setTotalQuestoesParcialmenteCorretas(
				float totalQuestoesParcialmenteCorretas) {
			this.totalQuestoesParcialmenteCorretas = totalQuestoesParcialmenteCorretas;
		}

		public AreaConhecimento getAreaConhecimento() {
			return areaConhecimento;
		}

		public void setAreaConhecimento(AreaConhecimento areaConhecimento) {
			this.areaConhecimento = areaConhecimento;
		}

		public float getTotalQuestoes() {
			return totalQuestoes;
		}

		public void setTotalQuestoes(float totalQuestoes) {
			this.totalQuestoes = totalQuestoes;
		}

		public float getTotalQuestoesCorretas() {
			return totalQuestoesCorretas;
		}

		public void setTotalQuestoesCorretas(float totalQuestoesCorretas) {
			this.totalQuestoesCorretas = totalQuestoesCorretas;
		}

		public float getNotaFinal() {
			return (totalQuestoesCorretas + totalQuestoesParcialmenteCorretas) / totalQuestoes;
		}

		public float getNotaFinalQuestoesCorretas() {
			return notaFinalQuestoesCorretas;
		}

		public void setNotaFinalQuestoesCorretas(float notaFinalQuestoesCorretas) {
			this.notaFinalQuestoesCorretas = notaFinalQuestoesCorretas;
		}

		public float getNotaFinalQuestoesParcialmenteCorretas() {
			return notaFinalQuestoesParcialmenteCorretas;
		}

		public void setNotaFinalQuestoesParcialmenteCorretas(
				float notaFinalQuestoesParcialmenteCorretas) {
			this.notaFinalQuestoesParcialmenteCorretas = notaFinalQuestoesParcialmenteCorretas;
		}

		public BigDecimal getNotaFinalAbsoluta() {
			BigDecimal valor = new BigDecimal(notaFinalQuestoesCorretas + notaFinalQuestoesParcialmenteCorretas);
			valor = valor.setScale(2, RoundingMode.HALF_UP);
			return valor;
			 
		}
		
		public BigDecimal getNotaFinalPercentual() {
			BigDecimal valor = new BigDecimal(getNotaFinalAbsoluta().doubleValue() / (totalQuestoes  * 1));
			valor =  valor.setScale(2,  RoundingMode.HALF_UP);
			return valor;
		}

	}

}
