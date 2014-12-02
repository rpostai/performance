package com.rp.performance.repository;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.TypedQuery;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import com.rp.performance.domain.prova.execucao.ExecucaoProva;
import com.rp.performance.domain.prova.execucao.ExecucaoProvaResposta;
import com.rp.performance.repository.jpa.prova.execucao.ExecucaoProvaRespostaRepository;

public class ExecucaoProvaRespostaRepositoryBeanTest extends
		AbstractRepositoryTest {

	@EJB
	private ExecucaoProvaRespostaRepository repository;

	@Test
	@UsingDataSet({ "fixture.xml", "candidato.xml", "execucao_prova.xml" })
	public void deveResponderUmaQuestaoCorretamente() {

		setDataAtual("2014-01-02 14:00:00");

		ExecucaoProva exec = em.find(ExecucaoProva.class, 100000l);
		exec.iniciarExecucao();
		em.merge(exec);
		em.flush();

		setDataAtual("2014-01-02 15:00:00");
		repository.responder("98492727478874", 100000l, Arrays.asList(300000l),
				null);

		TypedQuery<ExecucaoProvaResposta> tq = em.createQuery(
				"select o from ExecucaoProvaResposta o",
				ExecucaoProvaResposta.class);

		List<ExecucaoProvaResposta> list = tq.getResultList();

		Assert.assertEquals(1, list.size());
		Assert.assertEquals("98492727478874", list.get(0).getExecucaoProva()
				.getVoucher());
		Assert.assertEquals(new Long(100000l), list.get(0).getQuestao().getId());

		Assert.assertEquals(1, list.get(0).getRespostas().size());

	}
}
