package com.rp.performance.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import com.rp.performance.domain.prova.AlternativaQuestao;
import com.rp.performance.domain.prova.AreaConhecimento;
import com.rp.performance.domain.prova.Assunto;
import com.rp.performance.domain.prova.NivelDificuldade;
import com.rp.performance.domain.prova.Questao;
import com.rp.performance.domain.prova.TipoQuestao;
import com.rp.performance.repository.jpa.prova.QuestaoRepository;

public class QuestaoRepositoryBeanTest extends AbstractRepositoryTest {

	@EJB
	private QuestaoRepository repository;

	@Test
	@UsingDataSet({ "fixture.xml", "questao.xml" })
	public void deveInserirUmaQuestaoSimples() {

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

		repository.salvar(q1);

		Assert.assertNotNull(q1.getId());

	}

	@Test
	@UsingDataSet({"fixture.xml", "questao.xml"})
	public void deveRetornarUmaQuestaoCompletaComTodosOsObjetosFilhos() {
		
		AreaConhecimento ti = em.find(AreaConhecimento.class, 100000l);
		Assunto arquitetura = em.find(Assunto.class, 100000l);
		
		Set<Assunto> assuntos = new HashSet<Assunto>();
		assuntos.add(arquitetura);
		List<Questao> questoes = repository.pesquisarQuestoes(ti, assuntos);
		
		Assert.assertEquals(1,questoes.size());
		
		Questao q = questoes.get(0);
		Assert.assertEquals("O que significa JSON?", q.getQuestao());
		Assert.assertEquals(5, q.getAlternativas().size());
		Assert.assertEquals(1, q.getGabarito().size());
		Assert.assertEquals(2, q.getAnexos().size());
	}

}
