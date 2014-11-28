package com.rp.performance.repository;

import java.util.Calendar;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import com.rp.performance.domain.prova.Prova;
import com.rp.performance.domain.prova.execucao.Candidato;
import com.rp.performance.domain.prova.execucao.ExecucaoProva;
import com.rp.performance.repository.jpa.prova.execucao.ExecucaoProvaRepository;

public class ExecucaoProvaRepositoryBeanTest extends AbstractRepositoryTest {
	
	@EJB
	private ExecucaoProvaRepository repository;
	
	@Test
	@UsingDataSet({"fixture.xml","candidato.xml", "execucao_prova.xml"})
	public void deveGerarUmVoucher() {
		
		Candidato rodrigo = em.find(Candidato.class, 100000l);
		Prova provaJee = em.find(Prova.class, 100000l);
		
		Calendar dataInicial = Calendar.getInstance();
		dataInicial.set(2014, 10, 27);
		
		Calendar dataMaximaExecucao = Calendar.getInstance();
		dataMaximaExecucao.set(2014, 10, 30);
		
		String voucher = repository.gerarVoucher(rodrigo, provaJee, dataInicial.getTime(), dataMaximaExecucao.getTime(), 120);
		
		Assert.assertNotNull(voucher);
		
	}

}
