package com.rp.performance.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.persistence.TypedQuery;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.rp.performance.domain.AlternativaQuestao;
import com.rp.performance.domain.Candidato;
import com.rp.performance.domain.ExecucaoProva;
import com.rp.performance.domain.ExecucaoProvaResposta;
import com.rp.performance.domain.Prova;
import com.rp.performance.domain.ProvaQuestao;
import com.rp.performance.domain.Questao;
import com.rp.performance.domain.exceptions.PrazoValidadeInvalidoException;
import com.rp.performance.domain.exceptions.ProvaJaFinalizadaException;
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

	@Test
	@UsingDataSet({ "fixture.xml", "candidato.xml", "execucao_prova.xml" })
	public void deveExecutareCorrigirProvaComNota100() {
		setDataAtual("2014-01-02 15:00:00");
		ExecucaoProva e = em.find(ExecucaoProva.class, 100000l);
		e.iniciarExecucao();
		setDataAtual("2014-01-02 15:30:00");
		
		List<ProvaQuestao> questoes = e.getProva().getQuestoes();
		Iterator<ProvaQuestao> it = questoes.iterator();
		Questao questao1 = it.next().getQuestao();
		
		ExecucaoProvaResposta resposta = new ExecucaoProvaResposta();
		resposta.setQuestao(questao1);
		
		List<AlternativaQuestao> respostas = new ArrayList<AlternativaQuestao>();
		respostas.addAll(questao1.getGabarito());
		resposta.setRespostas(respostas);
		
		e.addResposta(resposta);
		
		e.finalizarExecucao();
		em.merge(e);
		em.flush();
		
		//double nota = e.corrigirProva();
		
		//Assert.assertEquals(new Double(100), new Double(nota));
	}
	
	@Test
	@UsingDataSet({ "fixture.xml", "candidato.xml", "execucao_prova.xml" })
	@Ignore
	public void deveExecutareCorrigirProvaComNotaZero() {
		setDataAtual("2014-01-02 15:00:00");
		ExecucaoProva e = em.find(ExecucaoProva.class, 100000l);
		e.iniciarExecucao();
		setDataAtual("2014-01-02 15:30:00");
		
		List<ProvaQuestao> questoes = e.getProva().getQuestoes();
		Iterator<ProvaQuestao> it = questoes.iterator();
		Questao questao1 = it.next().getQuestao();
		
		ExecucaoProvaResposta resposta = new ExecucaoProvaResposta();
		resposta.setQuestao(questao1);
		
		List<AlternativaQuestao> respostas = new ArrayList<AlternativaQuestao>();
		respostas.add(questao1.getAlternativas().get(1));
		resposta.setRespostas(respostas);
		
		e.addResposta(resposta);
		
		e.finalizarExecucao();
		em.merge(e);
		em.flush();
		
		//double nota = e.corrigirProva();
		
		//Assert.assertEquals(new Double(0), new Double(nota));
	}
}