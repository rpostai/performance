package com.rp.performance.repository;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import com.rp.performance.domain.AreaConhecimento;
import com.rp.performance.domain.Assunto;
import com.rp.performance.domain.Empresa;
import com.rp.performance.repository.jpa.prova.AreaConhecimentoRepository;

public class AreaConhecimentoRepositoryBeanTest extends AbstractRepositoryTest {

	@EJB
	private AreaConhecimentoRepository repository;

	@Test
	@UsingDataSet("fixture.xml")
	public void deveInserirUmaAreaConhecimento() {
		//empresa = em.find(Empresa.class, 1l);
		//empresa.getTelefones().size();

		AreaConhecimento area1 = new AreaConhecimento();
		area1.setDescricao("Tecnologia da Informação");
		//area1.setEmpresa(empresa);
		repository.salvar(area1);
		Assert.assertNotNull(area1.getId());
	}

	@Test
	@UsingDataSet({"fixture.xml", "area_conhecimento.xml" })
	public void deveRecuperarTodasAreasConhecimento() {
		List<AreaConhecimento> list = repository.getTodos();
		Assert.assertEquals(3, list.size());
	}
	
	@Test
	@UsingDataSet({"fixture.xml", "area_conhecimento.xml" })
	public void deveInserirUmAssuntoParaUmaAreadeConhecimento() {
		AreaConhecimento area = em.find(AreaConhecimento.class, 100000l);
		
		Assunto a1 = new Assunto();
		a1.setAssunto("Programação");
		area.addAssunto(a1);
		
		repository.salvar(area);
		
		area = em.find(AreaConhecimento.class, 100000l);
		Assert.assertEquals(1, area.getAssuntos().size());
		Assert.assertEquals("Programação", area.getAssuntos().iterator().next().getAssunto());
		
	}
	
	@Test
	@UsingDataSet({"fixture.xml", "area_conhecimento.xml" })
	public void deveRecuperarUmaAreaConhecimentoeAssuntos() {
		AreaConhecimento area = repository.get(200000l);
		Assert.assertNotNull(area);
		Assert.assertEquals("Contabilidade Empresarial", area.getAssuntos().iterator().next().getAssunto());
		
	}
	
	@Test
	@UsingDataSet({"fixture.xml", "area_conhecimento.xml" })
	public void deveExcluirAreaConhecimentoeAssuntos() {
		AreaConhecimento area = em.find(AreaConhecimento.class, 200000l);
		
		repository.excluir(area.getId());
		
		area = em.find(AreaConhecimento.class, 200000l);
		Assert.assertNull(area);
		
	}
	
	@Test
	@UsingDataSet({"fixture.xml", "area_conhecimento.xml" })
	public void deveInserirUmAssuntoFilhoParaumAssuntoJaExistente() {
		AreaConhecimento area = repository.get(200000l);
		Assunto assunto = area.getAssuntos().iterator().next();
		
		Assunto assuntoFilho = new Assunto();
		assuntoFilho.setAssunto("Fiscal e Tributária");
		
		assunto.addAssunto(assuntoFilho);
		
		repository.salvar(area);
		area = null;
		area = em.find(AreaConhecimento.class, 200000l);
		Assert.assertNotNull(area);
		Assunto a1 = area.getAssuntos().iterator().next();
		Assert.assertEquals(assunto, a1);
		
		Assunto a2 = a1.getAssuntos().iterator().next();
		Assert.assertEquals(a2, assuntoFilho);
		
	}
	
	@Test
	@UsingDataSet({"fixture.xml", "area_conhecimento.xml" })
	public void deveRecuperarAreaComVariosAssuntosFilhos() {
		AreaConhecimento area = repository.get(300000l);
		Assert.assertEquals(3, area.getAssuntos().size());
		
		Optional<Assunto> assunto = area.getAssuntos().stream().filter(subassunto-> {
			return subassunto.getAssuntos().size() > 0;
		}).findAny();
		
		Assert.assertTrue(assunto.isPresent());
		Assert.assertEquals(2, assunto.get().getAssuntos().size());
	}
	
	@Test
	@UsingDataSet({"fixture.xml", "area_conhecimento.xml" })
	public void deveExcluirAreaConhecimentoComVariosAssuntoseSubAssuntos() {
		repository.excluir(300000l);
		AreaConhecimento a = em.find(AreaConhecimento.class, 300000l);
		Assert.assertNull(a);
	}
	
	@Test
	@UsingDataSet({"fixture.xml", "area_conhecimento.xml" })
	public void deveInserirNovaAreaConhecimentoComVariosAssuntosFilhos() {
		AreaConhecimento a = new AreaConhecimento();
		a.setDescricao("Direito");
		
		Assunto a1 = new Assunto();
		a1.setAssunto("Direito Penal");
		
		Assunto a2 = new Assunto();
		a2.setAssunto("Direito Empresarial");
		
		Assunto a3 = new Assunto();
		a3.setAssunto("Direito Familiar");
		
		Assunto a4 = new Assunto();
		a4.setAssunto("Direito Trabalhista");
		
		Assunto a21 = new Assunto();
		a21.setAssunto("Direito Tributário");
		a2.addAssunto(a21);
		
		Assunto a22 = new Assunto();
		a22.setAssunto("Direito Xpto");
		a2.addAssunto(a22);
		
		Assunto a211 = new Assunto();
		a211.setAssunto("Direito Pequenas Empresas");
		a21.addAssunto(a211);
		
		a.addAssunto(a1);
		a.addAssunto(a2);
		a.addAssunto(a3);
		a.addAssunto(a4);
		
		repository.salvar(a);
		
		AreaConhecimento area = em.find(AreaConhecimento.class, a.getId());
		Assert.assertEquals(4, area.getAssuntos().size());
			
	}

}
