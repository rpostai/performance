package com.rp.performance.repository;

import java.util.Calendar;
import java.util.Optional;

import javax.ejb.EJB;
import javax.persistence.TypedQuery;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import com.rp.performance.domain.exceptions.PrazoValidadeInvalidoException;
import com.rp.performance.domain.exceptions.ProvaJaFinalizadaException;
import com.rp.performance.domain.prova.Prova;
import com.rp.performance.domain.prova.execucao.Candidato;
import com.rp.performance.domain.prova.execucao.ExecucaoProva;
import com.rp.performance.repository.jpa.prova.execucao.ExecucaoProvaRepository;

public class ExecucaoProvaRepositoryBeanTest extends AbstractRepositoryTest {

	@EJB
	private ExecucaoProvaRepository repository;

	@Test
	@UsingDataSet({ "fixture.xml", "candidato.xml", "execucao_prova.xml" })
	public void deveGerarUmVoucher() {

		Candidato rodrigo = em.find(Candidato.class, 100000l);
		Prova provaJee = em.find(Prova.class, 100000l);

		Calendar dataInicial = Calendar.getInstance();
		dataInicial.set(2014, 10, 27);

		Calendar dataMaximaExecucao = Calendar.getInstance();
		dataMaximaExecucao.set(2014, 10, 30);

		String voucher = repository.gerarVoucher(rodrigo, provaJee,
				dataInicial.getTime(), dataMaximaExecucao.getTime(), 120);

		Assert.assertNotNull(voucher);

		TypedQuery<ExecucaoProva> tq = em.createQuery(
				"select o from ExecucaoProva o where o.voucher = :voucher",
				ExecucaoProva.class);
		tq.setParameter("voucher", voucher);
		Assert.assertEquals(rodrigo, tq.getResultList().get(0).getCandidato());
		Assert.assertEquals(provaJee, tq.getResultList().get(0).getProva());
	}
	
	@Test
	@UsingDataSet({ "fixture.xml", "candidato.xml", "execucao_prova.xml" })
	public void deveResgatarUmaProvaPorVoucher() {
		Candidato rodrigo = em.find(Candidato.class, 100001l);
		Prova provaJee = em.find(Prova.class, 100001l);
		
		Optional<ExecucaoProva> ex = repository.recuperarProva("98492727478874");
		Assert.assertEquals(rodrigo.getId(), ex.get().getCandidato().getId());
		Assert.assertEquals(provaJee.getId(), ex.get().getProva().getId());
	}
	
	@Test
	@UsingDataSet({ "fixture.xml", "candidato.xml", "execucao_prova.xml" })
	public void deveIniciarUmaProvaComSucesso() {
		
		setDataAtual("2014-01-02 14:00:00");
		
		Optional<ExecucaoProva> ex = repository.recuperarProva("98492727478874");
		
		Assert.assertTrue(ex.isPresent());
		
		Prova prova = repository.iniciarExecucaoProva("98492727478874");
		
		ExecucaoProva exec1 = em.find(ExecucaoProva.class, ex.get().getId());
		Assert.assertNotNull(exec1.getDataInicio());
		
		Assert.assertNotNull(prova);
		Assert.assertNotNull(prova.getQuestoes());
	}
	
	@UsingDataSet({ "fixture.xml", "candidato.xml", "execucao_prova.xml" })
	@Test(expected=PrazoValidadeInvalidoException.class)
	public void naoDeveIniciarUmaProvaPoisTemPrazoVencido() {
		setDataAtual("2014-01-04 14:00:00");
		Optional<ExecucaoProva> ex = repository.recuperarProva("98492727478874");
		Assert.assertTrue(ex.isPresent());
		repository.iniciarExecucaoProva("98492727478874");
	}
	
	@Test(expected=RuntimeException.class)
	@UsingDataSet({ "fixture.xml", "candidato.xml", "execucao_prova.xml" })
	public void deveFalharAoTentarInicializarUmaProvaNaoIniciada() {
		Optional<ExecucaoProva> ex = repository.recuperarProva("98492727478874");
		Assert.assertTrue(ex.isPresent());
		repository.finalizarExecucaoProva("98492727478874");
	}
	
	@Test
	@UsingDataSet({ "fixture.xml", "candidato.xml", "execucao_prova.xml" })
	public void deveFinalizarumaProvaComSucesso() {
		setDataAtual("2014-01-02 16:00:00");
		Optional<ExecucaoProva> ex = repository.recuperarProva("98492727478874");
		Assert.assertTrue(ex.isPresent());
		repository.iniciarExecucaoProva("98492727478874");
		repository.finalizarExecucaoProva("98492727478874");
		ExecucaoProva exec1 = em.find(ExecucaoProva.class, ex.get().getId());
		Assert.assertNotNull(exec1.getDataConclusao());
	}
	
	@Test(expected=ProvaJaFinalizadaException.class)
	@UsingDataSet({ "fixture.xml", "candidato.xml", "execucao_prova.xml" })
	public void naoDeveFinalizarUmaProvaPoisJaFoiFinalizada() {
		setDataAtual("2014-01-02 15:00:00");
		ExecucaoProva e = em.find(ExecucaoProva.class, 100000l);
		e.iniciarExecucao();
		setDataAtual("2014-01-02 15:30:00");
		e.finalizarExecucao();
		em.merge(e);
		em.flush();
		
		Optional<ExecucaoProva> ex = repository.recuperarProva("98492727478874");
		Assert.assertTrue(ex.isPresent());
		setDataAtual("2014-01-02 17:00:00");
		repository.finalizarExecucaoProva("98492727478874");
		ExecucaoProva exec1 = em.find(ExecucaoProva.class, ex.get().getId());
		Assert.assertNotNull(exec1.getDataConclusao());
	}
	
	
	
	
	
}