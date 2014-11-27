package com.rp.performance.repository;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import com.rp.performance.domain.prova.AlternativaQuestao;
import com.rp.performance.domain.prova.AreaConhecimento;
import com.rp.performance.domain.prova.Assunto;
import com.rp.performance.domain.prova.NivelDificuldade;
import com.rp.performance.domain.prova.Prova;
import com.rp.performance.domain.prova.Questao;
import com.rp.performance.domain.prova.TipoQuestao;
import com.rp.performance.repository.jpa.prova.ProvaRepository;

public class ProvaRepositoryBeanTest extends AbstractRepositoryTest {

	@EJB
	private ProvaRepository repository;

	@Test
	@UsingDataSet({ "fixture.xml", "questao.xml" })
	public void deveInserirUmaProvaAPartirDeQuestoesProntas() {
		
		AreaConhecimento arquitetura = em.find(AreaConhecimento.class, 100000l);
		
		Prova prova = new Prova();
		prova.setDescricao("Programador Java J�nior");
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
		q1.setQuestao("Quais das alternativas abaixo fazem parte dos padr�es de projetos GoF?");
		
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
		prova.setDescricao("Programador Java J�nior");
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
		prova.setDescricao("Programador Java J�nior");
		prova.setAreaConhecimento(arquitetura);
		prova.setTempoDuracaoEmMinutos(120);
		
		Questao q1 = criaQuestao();
		
		prova.addQuestao(q1);

		repository.salvar(prova);

		Assert.assertNotNull(prova.getId());
		
		Prova p = repository.get (prova.getId());
		Assert.assertEquals(p.getDescricao(), prova.getDescricao());
		Assert.assertEquals(1, p.getQuestoes().size());
		Questao q = p.getQuestoes().iterator().next();
		Assert.assertEquals(1, q.getGabarito().size());
		Assert.assertEquals("Iterator, Builder, Singleton", q.getGabarito().iterator().next().getDescricao());
	}

}