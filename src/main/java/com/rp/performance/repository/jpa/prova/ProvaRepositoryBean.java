package com.rp.performance.repository.jpa.prova;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.rp.performance.domain.prova.Prova;
import com.rp.performance.repository.jpa.BaseRepository;

@Stateless
public class ProvaRepositoryBean extends BaseRepository<Prova> implements
		ProvaRepository {

	public ProvaRepositoryBean() {
		super(Prova.class);
	}

	@Override
	public Prova getProvaComAlternativas(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct p from Prova p ");
		sb.append("  join fetch p.questoes q");
		sb.append("  left join fetch q.alternativas");
//		sb.append("  left join fetch q.anexo");
		sb.append(" where p.id = :id");

		TypedQuery<Prova> list = em.createQuery(sb.toString(), Prova.class);
		list.setParameter("id", id);
		
		return list.getSingleResult();
	}
	
	public Prova getProvaComGabarito(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct p from Prova p ");
		sb.append("  join fetch p.questoes q");
		sb.append("  join fetch q.gabarito g");
		sb.append(" where p.id = :id");

		TypedQuery<Prova> list = em.createQuery(sb.toString(), Prova.class);
		list.setParameter("id", id);
		
		return list.getSingleResult();

	}

}
