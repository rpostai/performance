package com.rp.performance.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rp.performance.domain.RelatorioCorrecaoProva.Relatorio;
import com.rp.performance.domain.RelatorioCorrecaoProva.RelatorioAreaConhecimento;

public class RelatorioCorrecaoProvaTest {
	
	private ExecucaoProva execucao1;
	private ExecucaoProva execucao2;
	private ExecucaoProva execucao3;

	@Before
	public void setup() {
		execucao1 = FixtureExecucaoProva.criaCenario01();
		execucao2 = FixtureExecucaoProva.criaCenario02();
		execucao3 = FixtureExecucaoProva.criaCenario03();
	}

	
	@Test
	public void deveGerarRelatorioCorrecao() {
		List<CorrecaoProva> correcao = execucao1.corrigirProva();
		RelatorioCorrecaoProva relatorio = new RelatorioCorrecaoProva(correcao);
		Assert.assertNotNull(relatorio);
		List<RelatorioAreaConhecimento> relatorioPorArea = relatorio.getNotasPorAreaConhecimento();
		Assert.assertNotNull(relatorioPorArea);
		
		Assert.assertEquals(2,relatorioPorArea.size());
		
		Assert.assertEquals(new BigDecimal("1.00"), relatorioPorArea.get(1).getNotaFinalAbsoluta());
		Assert.assertEquals(new BigDecimal("1.00"), relatorioPorArea.get(1).getNotaFinalPercentual());
		
		Assert.assertEquals(new BigDecimal("2.67"), relatorioPorArea.get(0).getNotaFinalAbsoluta());
		Assert.assertEquals(new BigDecimal("0.67"), relatorioPorArea.get(0).getNotaFinalPercentual());
		
	}
	
	@Test
	public void deveGerarRelatorioPorNivelDificuldade() {
		List<CorrecaoProva> correcao = execucao1.corrigirProva();
		RelatorioCorrecaoProva relatorio = new RelatorioCorrecaoProva(correcao);
		Assert.assertNotNull(relatorio);
		List<Relatorio> relatorioPorNivel = relatorio.getNotasPorNivelDificuldade();
		Assert.assertNotNull(relatorioPorNivel);
		
		Assert.assertEquals(3,relatorioPorNivel.size());
		
		Assert.assertEquals("Fácil", ((NivelDificuldade)relatorioPorNivel.get(0).getAgrupador()).getDescricao());
		Assert.assertEquals("Muito difícil", ((NivelDificuldade)relatorioPorNivel.get(1).getAgrupador()).getDescricao());
		Assert.assertEquals("Médio", ((NivelDificuldade)relatorioPorNivel.get(2).getAgrupador()).getDescricao());
		
		Assert.assertEquals(2, relatorioPorNivel.get(0).getTotalQuestoes());
		Assert.assertEquals(new BigDecimal("0.67"), relatorioPorNivel.get(0).getNotaFinalAbsoluta());
		Assert.assertEquals(new BigDecimal("0.34"), relatorioPorNivel.get(0).getNotaFinalPercentual());
		
		Assert.assertEquals(1, relatorioPorNivel.get(1).getTotalQuestoes());
		Assert.assertEquals(new BigDecimal("1.00"), relatorioPorNivel.get(1).getNotaFinalAbsoluta());
		Assert.assertEquals(new BigDecimal("1.00"), relatorioPorNivel.get(1).getNotaFinalPercentual());
		
		Assert.assertEquals(2, relatorioPorNivel.get(2).getTotalQuestoes());
		Assert.assertEquals(new BigDecimal("2.00"), relatorioPorNivel.get(2).getNotaFinalAbsoluta());
		Assert.assertEquals(new BigDecimal("1.00"), relatorioPorNivel.get(2).getNotaFinalPercentual());

	}
	
	@Test
	public void deveGerarRelatorioPorAssuntos() {
		
		List<CorrecaoProva> correcao = execucao1.corrigirProva();
		RelatorioCorrecaoProva relatorio = new RelatorioCorrecaoProva(correcao);
		Assert.assertNotNull(relatorio);
		List<Relatorio> relatorioPorAssunto = relatorio.getNotasPorAssunto();
		Assert.assertNotNull(relatorioPorAssunto);
		
		Assert.assertEquals(4,relatorioPorAssunto.size());
		
		Assert.assertEquals("EJB", ((Assunto)relatorioPorAssunto.get(0).getAgrupador()).getAssunto());
		Assert.assertEquals("JDBC", ((Assunto)relatorioPorAssunto.get(1).getAgrupador()).getAssunto());
		Assert.assertEquals("JPA", ((Assunto)relatorioPorAssunto.get(2).getAgrupador()).getAssunto());
		Assert.assertEquals("Orientação a Objetos", ((Assunto)relatorioPorAssunto.get(3).getAgrupador()).getAssunto());
		
		Assert.assertEquals(2, relatorioPorAssunto.get(0).getTotalQuestoes());
		Assert.assertEquals(new BigDecimal("0.67"), relatorioPorAssunto.get(0).getNotaFinalAbsoluta());
		Assert.assertEquals(new BigDecimal("0.34"), relatorioPorAssunto.get(0).getNotaFinalPercentual());
		
		Assert.assertEquals(1, relatorioPorAssunto.get(1).getTotalQuestoes());
		Assert.assertEquals(new BigDecimal("1.00"), relatorioPorAssunto.get(1).getNotaFinalAbsoluta());
		Assert.assertEquals(new BigDecimal("1.00"), relatorioPorAssunto.get(1).getNotaFinalPercentual());
		
		Assert.assertEquals(1, relatorioPorAssunto.get(2).getTotalQuestoes());
		Assert.assertEquals(new BigDecimal("1.00"), relatorioPorAssunto.get(2).getNotaFinalAbsoluta());
		Assert.assertEquals(new BigDecimal("1.00"), relatorioPorAssunto.get(2).getNotaFinalPercentual());
		
		Assert.assertEquals(1, relatorioPorAssunto.get(3).getTotalQuestoes());
		Assert.assertEquals(new BigDecimal("1.00"), relatorioPorAssunto.get(3).getNotaFinalAbsoluta());
		Assert.assertEquals(new BigDecimal("1.00"), relatorioPorAssunto.get(3).getNotaFinalPercentual());
	}
	
	
	@Test
	public void deveGerarRelatorioCorrecaoCenario02() {
		List<CorrecaoProva> correcao = execucao2.corrigirProva();
		RelatorioCorrecaoProva relatorio = new RelatorioCorrecaoProva(correcao);
		Assert.assertNotNull(relatorio);
		
		Assert.assertEquals(new BigDecimal("4.33"), new BigDecimal(relatorio.getNotaFinal()).setScale(2, RoundingMode.HALF_UP));
		List<RelatorioAreaConhecimento> relatorioPorArea = relatorio.getNotasPorAreaConhecimento();
		Assert.assertNotNull(relatorioPorArea);
		
		Assert.assertEquals(2,relatorioPorArea.size());
		
		Assert.assertEquals(new BigDecimal("1.00"), relatorioPorArea.get(1).getNotaFinalAbsoluta());
		Assert.assertEquals(new BigDecimal("1.00"), relatorioPorArea.get(1).getNotaFinalPercentual());
		
		Assert.assertEquals(new BigDecimal("3.33"), relatorioPorArea.get(0).getNotaFinalAbsoluta());
		Assert.assertEquals(new BigDecimal("0.83"), relatorioPorArea.get(0).getNotaFinalPercentual());
		
	}
//	
	@Test
	public void deveGerarRelatorioCorrecaoCenario03() {
		List<CorrecaoProva> correcao = execucao3.corrigirProva();
		RelatorioCorrecaoProva relatorio = new RelatorioCorrecaoProva(correcao);
		Assert.assertNotNull(relatorio);
		
		Assert.assertEquals(new BigDecimal("3.00"), new BigDecimal(relatorio.getNotaFinal()).setScale(2, RoundingMode.HALF_UP));
		List<RelatorioAreaConhecimento> relatorioPorArea = relatorio.getNotasPorAreaConhecimento();
		Assert.assertNotNull(relatorioPorArea);
		
		Assert.assertEquals(2,relatorioPorArea.size());
		
		Assert.assertEquals(new BigDecimal("0.00"), relatorioPorArea.get(1).getNotaFinalAbsoluta());
		Assert.assertEquals(new BigDecimal("0.00"), relatorioPorArea.get(1).getNotaFinalPercentual());
		
		Assert.assertEquals(new BigDecimal("3.33"), relatorioPorArea.get(0).getNotaFinalAbsoluta());
		Assert.assertEquals(new BigDecimal("0.83"), relatorioPorArea.get(0).getNotaFinalPercentual());
		
	}
	

}
