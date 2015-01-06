package com.rp.performance.util;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.rp.performance.domain.utils.Estatistica;

public class EstatisticaTest {
	
	private List<Float> cenario1 = Arrays.asList(9f,7f,5f,3f,2f);
	private List<Float> cenario2 = Arrays.asList(9f,9f,9f,1f,1f, 1f);

	@Test
	public void deveCalcularMedia() {
		Assert.assertEquals(new Float("5.2"), Estatistica.calcularMedia(cenario1));
		
		Assert.assertEquals(new Float("5"), Estatistica.calcularMedia(cenario2));
	}
	
	@Test
	public void deveCalcularVariancia() {
		Assert.assertEquals(new Float("6.56"), Estatistica.calcularVariancia(new Float("5.2"), cenario1));
	}
	
	@Test
	public void deveCalcularDesvioPadrao() {
		Assert.assertEquals(new Float("2.56"), Estatistica.calcularDesvioPadrao(new Float("5.2"), cenario1));
		
		Assert.assertEquals(new Float("4"), Estatistica.calcularDesvioPadrao(new Float("5"), cenario2));
		
	}
	
}
