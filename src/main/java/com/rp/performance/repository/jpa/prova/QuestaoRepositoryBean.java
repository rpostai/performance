package com.rp.performance.repository.jpa.prova;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;

import com.rp.performance.domain.AreaConhecimento;
import com.rp.performance.domain.Assunto;
import com.rp.performance.domain.Questao;
import com.rp.performance.repository.jpa.BaseRepository;

@Stateless
public class QuestaoRepositoryBean extends BaseRepository<Questao> implements
		QuestaoRepository {

	public QuestaoRepositoryBean() {
		super(Questao.class);
	}

	@Override
	public List<Questao> pesquisarQuestoes(AreaConhecimento areaConhecimento,
			Set<Assunto> assuntos) {

		StringBuilder sb = new StringBuilder();
		sb.append("select distinct q from Questao q ");
		sb.append("   left join fetch q.assuntos assuntos");
		sb.append("   left join fetch q.alternativas alt");
		// sb.append("   left join fetch alt.anexo");
		sb.append("   left join fetch q.gabarito ");
		//sb.append("   left join fetch q.anexo ");
		sb.append("   where 1 = 1 ");

		if (areaConhecimento != null) {
			sb.append("and q.areaConhecimento = :area ");
		}

		if (CollectionUtils.isNotEmpty(assuntos)) {
			sb.append(" and assuntos in (:assuntos)");
		}

		TypedQuery<Questao> query = em.createQuery(sb.toString(), Questao.class);
		
		if (areaConhecimento != null) {
			query.setParameter("area", areaConhecimento);
		}

		if (CollectionUtils.isNotEmpty(assuntos)) {
			query.setParameter("assuntos", assuntos);
		}
		
		return query.getResultList();
	}

}
