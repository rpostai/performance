package com.rp.performance.repository.jpa.prova.execucao;

import java.util.List;

import com.rp.performance.domain.ExecucaoProvaResposta;
import com.rp.performance.repository.Repository;

public interface ExecucaoProvaRespostaRepository extends
		Repository<ExecucaoProvaResposta> {

	public void responder(String voucher,Long questaoId, List<Long> alternativa,
			String resposta);

}
