package com.rp.performance.repository;

import javax.ejb.EJB;

import org.junit.Assert;
import org.junit.Test;

import com.rp.performance.domain.Candidato;
import com.rp.performance.repository.jpa.prova.execucao.CandidatoRepository;

public class CandidatoRepositoryBeanTest extends AbstractRepositoryTest {

	@EJB
	private CandidatoRepository repository;

	@Test
	public void deveInserirUmCandidato() {
		Candidato c1 = new Candidato();
		c1.setNome("Rodrigo Postai");
		c1.setCpf("23545150909");
		repository.salvar(c1);
		Assert.assertNotNull(c1.getId());
	}
}
