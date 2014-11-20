package com.rp.performance.repository.jpa.prova;

import com.rp.performance.domain.prova.Prova;
import com.rp.performance.repository.Repository;

public interface ProvaRepository extends Repository<Prova> {

	public Prova getProvaComAlternativas(Long id);

	public Prova getProvaComGabarito(Long id);

}
