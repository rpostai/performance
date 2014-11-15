package com.rp.performance.repository.jpa.prova;

import javax.ejb.Stateless;

import com.rp.performance.domain.prova.AreaConhecimento;
import com.rp.performance.repository.jpa.BaseRepository;
import com.rp.performance.repository.prova.AreaConhecimentoRepository;

@Stateless
public class AreaConhecimentoRepositoryBean extends
		BaseRepository<AreaConhecimento> implements AreaConhecimentoRepository {

	public AreaConhecimentoRepositoryBean() {
		super(AreaConhecimento.class);
	}

}
