package com.rp.performance.repository.jpa.prova;

import javax.ejb.Stateless;

import com.rp.performance.domain.prova.NivelDificuldade;
import com.rp.performance.repository.jpa.BaseRepository;

@Stateless
public class NivelDificuldaderepositoryBean extends
		BaseRepository<NivelDificuldade> implements NivelDificuldadeRepository {

	public NivelDificuldaderepositoryBean() {
		super(NivelDificuldade.class);
	}

}
