package com.rp.performance.repository.jpa.prova.execucao;

import javax.ejb.Stateless;

import com.rp.performance.domain.prova.execucao.Candidato;
import com.rp.performance.repository.jpa.BaseRepository;

@Stateless
public class CandidatoRepositoryBean extends BaseRepository<Candidato>
		implements CandidatoRepository {

	public CandidatoRepositoryBean() {
		super(Candidato.class);
	}

}
