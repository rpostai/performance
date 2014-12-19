package com.rp.performance.domain;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProvaTest {
	
	private Prova p;

	@Before
	public void setup() {
		
		NivelDificuldade n = new NivelDificuldade();
		n.setDescricao("Muito difícil");
		
		AreaConhecimento a = new AreaConhecimento();
		a.setDescricao("Java");
		
		Assunto as = new Assunto();
		as.setAssunto("Spring");
		
		a.addAssunto(as);
		
		Questao q = new Questao();
		q.setAreaConhecimento(a);
		q.addAssunto(as);
		q.setQuestao("O que é Spring?");
		q.setNivelDificuldade(n);
		
		AlternativaQuestao aq = new AlternativaQuestao();
		aq.setDescricao("opcao 1");
		//aq.setQuestao(q);
		
		AlternativaQuestao aq2 = new AlternativaQuestao();
		aq2.setDescricao("opcao 2");
		//aq2.setQuestao(q);
		
		q.addAlternativa(aq);
		q.addAlternativa(aq2);
		q.addGabarito(aq2);
		
		p = new Prova();
		p.addQuestao(q);
		p.setAreaConhecimento(a);
		p.setDescricao("Prova Básica de Spring");
		p.setOrientacoes("Orientacao 1");
		p.setTempoDuracaoEmMinutos(100);
	}

	@Test
	public void deveCriarUmaVersaoProvaComSucesso() {
		Prova p1 = p.criarVersaoProva();
		Assert.assertEquals(p.getDescricao(), p1.getDescricao());
		Assert.assertEquals(p.getTempoDuracaoEmMinutos(), p1.getTempoDuracaoEmMinutos());
		Assert.assertEquals(p.getQuestoes().size(), p1.getQuestoes().size());
		Assert.assertEquals(p.getQuestoes().iterator().next(), p1.getQuestoes().iterator().next());
		Assert.assertTrue(p != p1);
	}

}
