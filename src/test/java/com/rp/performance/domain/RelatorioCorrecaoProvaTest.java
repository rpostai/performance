package com.rp.performance.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rp.performance.domain.RelatorioCorrecaoProva.Relatorio;
import com.rp.performance.domain.RelatorioCorrecaoProva.RelatorioAreaConhecimento;

public class RelatorioCorrecaoProvaTest {
	
	private ExecucaoProva execucao;

	@Before
	public void setup() {
		testCorrecaoProva();
	}

	public void testCorrecaoProva() {
		NivelDificuldade muitoDificil = new NivelDificuldade();
		muitoDificil.setId(1l);
		muitoDificil.setDescricao("Muito difícil");

		NivelDificuldade medio = new NivelDificuldade();
		medio.setId(2l);
		medio.setDescricao("Médio");

		NivelDificuldade facil = new NivelDificuldade();
		facil.setId(3l);
		facil.setDescricao("Fácil");

		AreaConhecimento javase = new AreaConhecimento();
		javase.setId(1l);
		javase.setDescricao("Java SE");

		AreaConhecimento javaee = new AreaConhecimento();
		javaee.setId(2l);
		javaee.setDescricao("Java EE");

		AreaConhecimento javame = new AreaConhecimento();
		javame.setId(3l);
		javame.setDescricao("Java ME");

		Assunto orientacaoObjetos = new Assunto();
		orientacaoObjetos.setId(1l);
		orientacaoObjetos.setAssunto("Orientação a Objetos");

		Assunto jpa = new Assunto();
		jpa.setId(2l);
		jpa.setAssunto("JPA");

		Assunto ejb = new Assunto();
		ejb.setId(3l);
		ejb.setAssunto("EJB");

		Assunto jdbc = new Assunto();
		jdbc.setId(4l);
		jdbc.setAssunto("JDBC");

		Assunto threads = new Assunto();
		threads.setId(5l);
		threads.setAssunto("Threads");

		javase.addAssunto(orientacaoObjetos);
		javase.addAssunto(threads);
		javase.addAssunto(jdbc);
		javaee.addAssunto(ejb);
		javaee.addAssunto(jpa);

		Questao q1 = questao1(muitoDificil, javase, orientacaoObjetos);
		ProvaQuestao pq1 = new ProvaQuestao(1, 1, q1);
		
		Questao q2 = questao2(facil, javaee, ejb);
		ProvaQuestao pq2 = new ProvaQuestao(1, 1, q2);
		
		Questao q3 = questao3(facil, javaee, ejb);
		ProvaQuestao pq3 = new ProvaQuestao(1, 1, q3);
		
		Questao q4 = questao4(medio, javaee, jpa);
		ProvaQuestao pq4 = new ProvaQuestao(1, 1, q4);
		
		Questao q5 = questao5(medio, javaee, jdbc);
		ProvaQuestao pq5 = new ProvaQuestao(1, 1, q5);

		Prova p = new Prova();
		p.setId(1l);
		p.addQuestao(pq1);
		p.addQuestao(pq2);
		p.addQuestao(pq3);
		p.addQuestao(pq4);
		p.addQuestao(pq5);

		p.setAreaConhecimento(javaee);
		p.setDescricao("Prova Analista de Sistemas Java Júnior");
		p.setOrientacoes("O tempo de execução da prova é de 60 minutos");
		p.setTempoDuracaoEmMinutos(60);

		Candidato c = new Candidato();
		c.setCpf("02644103940");
		c.setNome("Rodrigo Postai");

		execucao = new ExecucaoProva();
		execucao.setProva(p);
		execucao.setCandidato(c);

		Calendar dataAbertura = Calendar.getInstance();
		dataAbertura.set(2014, 11, 15);

		Calendar datavalidade = Calendar.getInstance();
		datavalidade.setTime(dataAbertura.getTime());
		datavalidade.add(Calendar.DAY_OF_MONTH, 3);

		execucao.setDataAbertura(dataAbertura.getTime());
		execucao.setDataValidade(datavalidade.getTime());

		System.setProperty("DATA", "2014-12-16 14:00:00");

		execucao.iniciarExecucao();

		Iterator<ProvaQuestao> questoes = p.getQuestoes().iterator();

		// Acertou 100%
		ExecucaoProvaResposta resposta1 = new ExecucaoProvaResposta();
		Questao aux = questoes.next().getQuestao();
		resposta1.setQuestao(aux);
		resposta1.setRespostas(aux.getGabarito());

		// Acertou 2/3
		ExecucaoProvaResposta resposta2 = new ExecucaoProvaResposta();
		aux = questoes.next().getQuestao();
		resposta2.setQuestao(aux);
		List<AlternativaQuestao> resp2 = new ArrayList<AlternativaQuestao>();
		resp2.add(aux.getGabarito().get(0));
		resp2.add(aux.getGabarito().get(1));
		resposta2.setRespostas(resp2);

		// Errou
		ExecucaoProvaResposta resposta3 = new ExecucaoProvaResposta();
		aux = questoes.next().getQuestao();
		resposta3.setQuestao(aux);
		List<AlternativaQuestao> resp3 = new ArrayList<AlternativaQuestao>();
		resp3.add(aux.getAlternativas().get(0));
		resposta3.setRespostas(resp3);

		// Acertou 100%
		ExecucaoProvaResposta resposta4 = new ExecucaoProvaResposta();
		aux = questoes.next().getQuestao();
		resposta4.setQuestao(aux);
		List<AlternativaQuestao> resp4 = new ArrayList<AlternativaQuestao>();
		resp4.add(aux.getGabarito().get(0));
		resposta4.setRespostas(resp4);

		// Acertou 100%
		ExecucaoProvaResposta resposta5 = new ExecucaoProvaResposta();
		aux = questoes.next().getQuestao();
		resposta5.setQuestao(aux);
		List<AlternativaQuestao> resp5 = new ArrayList<AlternativaQuestao>();
		resp5.add(aux.getGabarito().get(0));
		resposta5.setRespostas(resp5);
		
		execucao.addResposta(resposta1);
		execucao.addResposta(resposta2);
		execucao.addResposta(resposta3);
		execucao.addResposta(resposta4);
		execucao.addResposta(resposta5);

		execucao.finalizarExecucao();
	}

	private Questao questao1(NivelDificuldade muitoDificil,
			AreaConhecimento javase, Assunto orientacaoObjetos) {
		Questao q1 = new Questao();
		q1.setId(1l);
		q1.setTipoQuestao(TipoQuestao.MULTIPLA_ESCOLHA);
		q1.setAreaConhecimento(javase);
		q1.addAssunto(orientacaoObjetos);
		q1.setQuestao("Indique as alternativas corretas sobre polimorfismo");
		q1.setNivelDificuldade(muitoDificil);

		AlternativaQuestao aq1 = new AlternativaQuestao();
		aq1.setId(17l);
		aq1.setDescricao("Poliformismo é a capacidade que um objeto tem de assumir diversas formas em tempo de execução");
		//aq1.setQuestao(q1);

		AlternativaQuestao aq2 = new AlternativaQuestao();
		aq2.setId(18l);
		aq2.setDescricao("Polimorfismo está intimamente ligado ao conceito de herança");
		//aq2.setQuestao(q1);

		AlternativaQuestao aq3 = new AlternativaQuestao();
		aq3.setId(19l);
		aq3.setDescricao("Para implementar polimorfismo é obrigatório o uso de interfaces");
		//aq3.setQuestao(q1);

		AlternativaQuestao aq4 = new AlternativaQuestao();
		aq4.setId(20l);
		aq4.setDescricao("Um objeto polimorfico em Java pode assumir características de vários objetos através de herança múltipla");
		//aq4.setQuestao(q1);

		q1.addAlternativa(aq1);
		q1.addAlternativa(aq2);
		q1.addAlternativa(aq3);
		q1.addAlternativa(aq4);

		q1.addGabarito(aq1);
		q1.addGabarito(aq2);
		return q1;
	}

	private Questao questao2(NivelDificuldade nivelDificuldade,
			AreaConhecimento area, Assunto assunto) {
		Questao q1 = new Questao();
		q1.setId(2l);
		q1.setTipoQuestao(TipoQuestao.MULTIPLA_ESCOLHA);
		q1.setAreaConhecimento(area);
		q1.addAssunto(assunto);
		q1.setQuestao("Quando o desenvolvimento de um sistema utilizando a tecnologia JEE é adequada? Marque todas as alternativas corretas");
		q1.setNivelDificuldade(nivelDificuldade);

		AlternativaQuestao aq1 = new AlternativaQuestao();
		aq1.setId(13l);
		aq1.setDescricao("(a) quando um sistema é distribuído");
		//aq1.setQuestao(q1);

		AlternativaQuestao aq2 = new AlternativaQuestao();
		aq2.setId(14l);
		aq2.setDescricao("(b) quando um sistema é transacional");
		//aq2.setQuestao(q1);

		AlternativaQuestao aq3 = new AlternativaQuestao();
		aq3.setId(15l);
		aq3.setDescricao("(c) quando você está desenvolvendo um sistema on somente são servidas páginas estáticas");
		//aq3.setQuestao(q1);

		AlternativaQuestao aq4 = new AlternativaQuestao();
		aq4.setId(16l);
		aq4.setDescricao("(d) quando requisitos relacionados a segurança de acesso aos serviços oferecidos pelo sistema são essenciais");
		//aq4.setQuestao(q1);

		q1.addAlternativa(aq1);
		q1.addAlternativa(aq2);
		q1.addAlternativa(aq3);
		q1.addAlternativa(aq4);

		q1.addGabarito(aq1);
		q1.addGabarito(aq2);
		q1.addGabarito(aq4);
		return q1;
	}

	private Questao questao3(NivelDificuldade nivelDificuldade,
			AreaConhecimento area, Assunto assunto) {
		Questao q1 = new Questao();
		q1.setId(3l);
		q1.setTipoQuestao(TipoQuestao.ESCOLHA_UNICA);
		q1.setAreaConhecimento(area);
		q1.addAssunto(assunto);
		q1.setQuestao("Qual a função da anotação @Singleton no contexto de JEE?");
		q1.setNivelDificuldade(nivelDificuldade);

		AlternativaQuestao aq1 = new AlternativaQuestao();
		aq1.setId(9l);
		aq1.setDescricao("Tornar uma instância de objeto única");
		//aq1.setQuestao(q1);

		AlternativaQuestao aq2 = new AlternativaQuestao();
		aq2.setId(10l);
		aq2.setDescricao("Tornar uma instância de objeto única por JVM");
		//aq2.setQuestao(q1);

		AlternativaQuestao aq3 = new AlternativaQuestao();
		aq3.setId(11l);
		aq3.setDescricao("Tornar uma classe controlada pela JVM");
		//aq3.setQuestao(q1);

		AlternativaQuestao aq4 = new AlternativaQuestao();
		aq4.setId(12l);
		aq4.setDescricao("Permitir que uma classe tenha acesso paralelo");
		//aq4.setQuestao(q1);

		q1.addAlternativa(aq1);
		q1.addAlternativa(aq2);
		q1.addAlternativa(aq3);
		q1.addAlternativa(aq4);

		q1.addGabarito(aq2);
		return q1;
	}

	private Questao questao4(NivelDificuldade nivelDificuldade,
			AreaConhecimento area, Assunto assunto) {
		Questao q1 = new Questao();
		q1.setId(4l);
		q1.setTipoQuestao(TipoQuestao.ESCOLHA_UNICA);
		q1.setAreaConhecimento(area);
		q1.addAssunto(assunto);
		q1.setQuestao("@GET e @POST no contexto de JEE são utilizados para:");
		q1.setNivelDificuldade(nivelDificuldade);

		AlternativaQuestao aq1 = new AlternativaQuestao();
		aq1.setId(5l);
		aq1.setDescricao("Tornar uma classe POJO em um EJB");
		//aq1.setQuestao(q1);

		AlternativaQuestao aq2 = new AlternativaQuestao();
		aq2.setId(6l);
		aq2.setDescricao("Tornar uma classe POJO em um objeto Singleton");
		//aq2.setQuestao(q1);

		AlternativaQuestao aq3 = new AlternativaQuestao();
		aq3.setId(7l);
		aq3.setDescricao("Tornar uma classe POJO em um Webservice REST");
		//aq3.setQuestao(q1);

		AlternativaQuestao aq4 = new AlternativaQuestao();
		aq4.setId(8l);
		aq4.setDescricao("Nenhuma das alternativas");
		//aq4.setQuestao(q1);

		q1.addAlternativa(aq1);
		q1.addAlternativa(aq2);
		q1.addAlternativa(aq3);
		q1.addAlternativa(aq4);

		q1.addGabarito(aq3);
		return q1;
	}

	private Questao questao5(NivelDificuldade nivelDificuldade,
			AreaConhecimento area, Assunto assunto) {
		Questao q1 = new Questao();
		q1.setId(5l);
		q1.setTipoQuestao(TipoQuestao.ESCOLHA_UNICA);
		q1.setAreaConhecimento(area);
		q1.addAssunto(assunto);
		q1.setQuestao("Qual a função da @Transaction(TransactionType.REQUIRES_NEW) ?");
		q1.setNivelDificuldade(nivelDificuldade);

		AlternativaQuestao aq1 = new AlternativaQuestao();
		aq1.setId(1l);
		aq1.setDescricao("Tornar um Stateless Session Bean transacional");
		//aq1.setQuestao(q1);

		AlternativaQuestao aq2 = new AlternativaQuestao();
		aq2.setId(2l);
		aq2.setDescricao("Criar uma nova transação sempre que o método deste classe for chamado");
		//aq2.setQuestao(q1);

		AlternativaQuestao aq3 = new AlternativaQuestao();
		aq3.setId(3l);
		aq3.setDescricao("Solicitar que o cliente crie uma transação antes de chamar o método deste EJB");
		//aq3.setQuestao(q1);

		AlternativaQuestao aq4 = new AlternativaQuestao();
		aq4.setId(4l);
		aq4.setDescricao("Nenhuma das alternativas");
		//aq4.setQuestao(q1);

		q1.addAlternativa(aq1);
		q1.addAlternativa(aq2);
		q1.addAlternativa(aq3);
		q1.addAlternativa(aq4);

		q1.addGabarito(aq2);
		return q1;
	}
	
	@Test
	public void deveGerarRelatorioCorrecao() {
		List<CorrecaoProva> correcao = execucao.corrigirProva();
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
		List<CorrecaoProva> correcao = execucao.corrigirProva();
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
		
		List<CorrecaoProva> correcao = execucao.corrigirProva();
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

}
