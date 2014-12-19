package com.rp.performance.repository;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import com.rp.performance.domain.AlternativaQuestao;
import com.rp.performance.domain.AreaConhecimento;
import com.rp.performance.domain.Assunto;
import com.rp.performance.domain.NivelDificuldade;
import com.rp.performance.domain.Prova;
import com.rp.performance.domain.Questao;
import com.rp.performance.domain.TipoQuestao;
import com.rp.performance.repository.jpa.prova.ProvaRepository;

public class ProvaRepositoryBeanTest extends AbstractRepositoryTest {

	@EJB
	private ProvaRepository repository;

	@Test
	@UsingDataSet({ "fixture.xml", "questao.xml" })
	public void deveInserirUmaProvaAPartirDeQuestoesProntas() {

		AreaConhecimento arquitetura = em.find(AreaConhecimento.class, 100000l);

		Prova prova = new Prova();
		prova.setDescricao("Programador Java Júnior");
		prova.setTempoDuracaoEmMinutos(180);
		prova.setAreaConhecimento(arquitetura);

		Questao q1 = em.find(Questao.class, 100000l);
		prova.addQuestao(q1);

		repository.salvar(prova);

		Assert.assertNotNull(prova.getId());
	}

	private Questao criaQuestao() {
		NivelDificuldade muitoDificil = em
				.find(NivelDificuldade.class, 400000l);
		AreaConhecimento ti = em.find(AreaConhecimento.class, 100000l);
		Assunto arquitetura = em.find(Assunto.class, 100000l);

		Questao q1 = new Questao();
		q1.setNivelDificuldade(muitoDificil);
		q1.setAreaConhecimento(ti);
		q1.addAssunto(arquitetura);
		q1.setTipoQuestao(TipoQuestao.ESCOLHA_UNICA);
		q1.setQuestao("Quais das alternativas abaixo fazem parte dos padrões de projetos GoF?");

		q1.addAnexo("anexo1");
		q1.addAnexo("anexo2");

		AlternativaQuestao a = new AlternativaQuestao();
		a.setDescricao("Singleton, Composite, MVC");

		AlternativaQuestao b = new AlternativaQuestao();
		b.setDescricao("Decorator, Builder, EAI");

		AlternativaQuestao c = new AlternativaQuestao();
		c.setDescricao("Iterator, Builder, Singleton");

		AlternativaQuestao d = new AlternativaQuestao();
		d.setDescricao("MVC, MVVP, MVP");

		AlternativaQuestao e = new AlternativaQuestao();
		e.setDescricao("MVC, MVVP, MVP");

		q1.addAlternativa(a);
		q1.addAlternativa(b);
		q1.addAlternativa(c);
		q1.addAlternativa(d);
		q1.addAlternativa(e);

		q1.addGabarito(c);

		return q1;
	}

	@Test
	@UsingDataSet({ "fixture.xml", "questao.xml" })
	public void deveInserirUmaProvaComQuestaoNova() {

		AreaConhecimento arquitetura = em.find(AreaConhecimento.class, 100000l);

		Prova prova = new Prova();
		prova.setDescricao("Programador Java Júnior");
		prova.setAreaConhecimento(arquitetura);
		prova.setTempoDuracaoEmMinutos(120);

		Questao q1 = criaQuestao();

		prova.addQuestao(q1);

		repository.salvar(prova);

		Assert.assertNotNull(prova.getId());

		Prova p = repository.getProvaComAlternativas(prova.getId());
		Assert.assertEquals(p.getDescricao(), prova.getDescricao());
		Assert.assertEquals(1, p.getQuestoes().size());
		Questao q = p.getQuestoes().iterator().next();
		Assert.assertEquals(2, q.getAnexos().size());
	}

	@Test
	@UsingDataSet({ "fixture.xml", "questao.xml" })
	public void deveInserirUmaProvaERecuperarGabarito() {

		AreaConhecimento arquitetura = em.find(AreaConhecimento.class, 100000l);

		Prova prova = new Prova();
		prova.setDescricao("Programador Java Júnior");
		prova.setAreaConhecimento(arquitetura);
		prova.setTempoDuracaoEmMinutos(120);

		Questao q1 = criaQuestao();

		prova.addQuestao(q1);

		repository.salvar(prova);

		Assert.assertNotNull(prova.getId());

		Prova p = repository.get(prova.getId());
		Assert.assertEquals(p.getDescricao(), prova.getDescricao());
		Assert.assertEquals(1, p.getQuestoes().size());
		Questao q = p.getQuestoes().iterator().next();
		Assert.assertEquals(1, q.getGabarito().size());
		Assert.assertEquals("Iterator, Builder, Singleton", q.getGabarito()
				.iterator().next().getDescricao());
	}

	@Test
	@UsingDataSet({ "fixture.xml", "execucao_prova.xml" })
	public void deveRecuperarUmaProvaCompletaComSucesso() {
		Prova p = repository.getProvaCompleta(100001l);
		Assert.assertNotNull(p);
		Assert.assertEquals("Tecnologia da Informação", p.getAreaConhecimento()
				.getDescricao());
		Assert.assertEquals("JEE 7.1", p.getDescricao());
		Assert.assertEquals(1, p.getQuestoes().size());
		Questao q = p.getQuestoes().iterator().next();
		Assert.assertEquals("O que significa JSON?", q.getQuestao());
		Assert.assertEquals("Médio", q.getNivelDificuldade().getDescricao());
		Assert.assertEquals(5, q.getAlternativas().size());
		Assert.assertEquals(1, q.getGabarito().size());
		AlternativaQuestao g = q.getGabarito().iterator().next();
		Assert.assertEquals("Javascript Object Notation",g.getDescricao());
	}
	
	@Test
	@UsingDataSet({ "fixture.xml", "execucao_prova.xml" })
	public void deveGerarUmaVersaoProvaComSucesso() {
		Prova matriz = repository.getProvaCompleta(100001l);
		Prova versao = repository.gerarVersao(100001l);
		Assert.assertNotEquals(versao.getId().longValue(), 100001l);
		Assert.assertEquals(versao.getQuestoes().size(), matriz.getQuestoes().size());
		Assert.assertEquals(versao.getOrientacoes(), matriz.getOrientacoes());
		Assert.assertEquals(versao.getTempoDuracaoEmMinutos(), matriz.getTempoDuracaoEmMinutos());
		Questao qm = matriz.getQuestoes().iterator().next();
		Questao qv = versao.getQuestoes().iterator().next();
		
		Assert.assertEquals(qv.getQuestao(), qm.getQuestao());
		Assert.assertNotEquals(qv.getId(), qm.getId());
		Assert.assertEquals(qv.getNivelDificuldade().getDescricao(), qm.getNivelDificuldade().getDescricao());
		
		AlternativaQuestao aqm = qm.getGabarito().iterator().next();
		AlternativaQuestao aqv = qv.getGabarito().iterator().next();
		
		Assert.assertEquals(aqv.getDescricao(), aqm.getDescricao());
		Assert.assertNotEquals(aqv.getId(), aqm.getId());
		
	}

}
