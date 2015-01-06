package com.rp.performance.domain.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

public class Estatistica {

	public static Float calcularMedia(List<Float> valores) {
		return valores.stream().reduce((valor, acumulador) -> {
			return valor + acumulador;
		}).get() / valores.size();
	}

	public static Float calcularDesvioPadrao(Float media, List<Float> valores) {
		Float variancia = calcularVariancia(media, valores);
		Double d = Math.sqrt(variancia);
		BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN);
		d = bd.doubleValue();
		return d.floatValue();
	}

	public static Float calcularVariancia(Float media, List<Float> valores) {
		Stream<Float> desviosQuadrado = calcularDesviosQuadrado(media, valores);

		Float somaQuadradoDesvio = desviosQuadrado.reduce(
				(prev, acumulador) -> {
					return prev + acumulador;
				}).get();

		return somaQuadradoDesvio / valores.size();
	}

	private static Stream<Float> calcularDesviosQuadrado(Float media,
			List<Float> valores) {
		return valores.stream().map(valor -> {
			Double d = Math.pow((media - valor), 2);
			return d.floatValue();
		});
	}

}
