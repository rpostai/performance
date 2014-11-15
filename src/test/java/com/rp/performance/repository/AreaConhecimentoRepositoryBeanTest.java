package com.rp.performance.repository;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import com.rp.performance.domain.Empresa;
import com.rp.performance.domain.prova.AreaConhecimento;
import com.rp.performance.repository.prova.AreaConhecimentoRepository;

public class AreaConhecimentoRepositoryBeanTest extends AbstractRepositoryTest {

	@EJB
	private AreaConhecimentoRepository repository;

	private Empresa empresa;

	@Test
	@UsingDataSet("fixture.xml")
	public void deveInserirUmaAreaConhecimento() {
		empresa = em.find(Empresa.class, 1l);
		empresa.getTelefones().size();

		AreaConhecimento area1 = new AreaConhecimento();
		area1.setDescricao("Tecnologia da Informação");
		area1.setEmpresa(empresa);
		repository.salvar(area1);
		Assert.assertNotNull(area1.getId());
	}

	@Test
	@UsingDataSet({"fixture.xml", "area_conhecimento.xml" })
	public void deveRecuperarTodasAreasConhecimento() {
		List<AreaConhecimento> list = repository.getTodos();
		Assert.assertEquals(3, list.size());
	}

}
