package com.rp.performance.repository;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import com.rp.performance.domain.NivelDificuldade;
import com.rp.performance.repository.jpa.prova.NivelDificuldadeRepository;

public class NivelDificuldadeRepositoryBeanTest extends AbstractRepositoryTest {

	@EJB
	private NivelDificuldadeRepository repository;

	@Test
	public void deveInserirUmNivelDificuldade() {
		NivelDificuldade n1 = new NivelDificuldade();
		n1.setDescricao("Fácil");
		repository.salvar(n1);
		Assert.assertNotNull(n1.getId());
	}
	
	@Test
	@UsingDataSet("nivel_dificuldade.xml")
	public void deveRecuperarTodosOsNiveisDificuldade() {
		List<NivelDificuldade> list = repository.getTodos();
		Assert.assertEquals(3, list.size());
	}
	
	@Test
	@UsingDataSet("nivel_dificuldade.xml")
	public void deveAtualizarONivelDificuldadeDificilParaMuitoDificil() {
		NivelDificuldade n = em.find(NivelDificuldade.class, 300000l);
		Assert.assertEquals("Dificil", n.getDescricao());
		n.setDescricao("Muito difícil");
		repository.salvar(n);
		n = em.find(NivelDificuldade.class, 300000l);
		Assert.assertEquals("Muito difícil", n.getDescricao());
	}
	
	@Test
	@UsingDataSet("nivel_dificuldade.xml")
	public void deveExcluirNivelDificuldadeSemFilhos() {
		repository.excluir(200000l);
		NivelDificuldade n = em.find(NivelDificuldade.class, 200000l);
		Assert.assertNull(n);
	}

}
