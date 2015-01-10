package com.rp.performance.domain;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RelatorioComparacaoCandidatoTest {
	
	ExecucaoProva execucao1;
	ExecucaoProva execucao2;
	
	@Before
	public void setup() {
		execucao1 = FixtureExecucaoProva.criaCenario01();
		execucao2 = FixtureExecucaoProva.criaCenario02();
	}
	
	@Test
	public void deveGerarRelatorioComparacao() {
		RelatorioComparacaoCandidato r = new RelatorioComparacaoCandidato(execucao1, execucao2);
		List<RelatorioComparacao> relatorio = r.gerarRelatoroio();
		
	}

}
