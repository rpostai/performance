package com.rp.performance.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import com.rp.performance.domain.BaseEntity;
import com.rp.performance.domain.Empresa;
import com.rp.performance.domain.prova.Prova;
import com.rp.performance.domain.prova.execucao.Candidato;
import com.rp.performance.repository.jpa.BaseRepository;
import com.rp.performance.repository.jpa.listener.RepositoryEntityListener;
import com.rp.performance.repository.jpa.prova.AreaConhecimentoRepositoryBean;
import com.rp.performance.repository.prova.AreaConhecimentoRepository;

@RunWith(Arquillian.class)
@CleanupUsingScript("clean.sql")
public abstract class AbstractRepositoryTest {
	
	@PersistenceContext(unitName="performancePU")
	protected EntityManager em;
	
	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class)
				.addPackage(BaseEntity.class.getPackage())
				.addPackage(Prova.class.getPackage())
				.addPackage(Candidato.class.getPackage())
				.addPackage(RepositoryEntityListener.class.getPackage())
				.addPackage(Repository.class.getPackage())
				.addPackage(BaseRepository.class.getPackage())
				.addPackage(AreaConhecimentoRepository.class.getPackage())
				.addPackage(RepositoryEntityListener.class.getPackage())
				.addPackage(AreaConhecimentoRepositoryBean.class.getPackage())
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("test-persistence.xml","META-INF/persistence.xml")
				.addAsWebInfResource("performance-ds.xml");
	}

}
