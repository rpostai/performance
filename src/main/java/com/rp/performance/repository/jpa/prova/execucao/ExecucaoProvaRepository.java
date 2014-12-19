package com.rp.performance.repository.jpa.prova.execucao;

import java.util.Date;
import java.util.Optional;

import com.rp.performance.domain.Candidato;
import com.rp.performance.domain.ExecucaoProva;
import com.rp.performance.domain.Prova;
import com.rp.performance.repository.Repository;

public interface ExecucaoProvaRepository extends Repository<ExecucaoProva> {

	String gerarVoucher(Candidato candidato, Prova prova,
			Date dataAbertura, Date dataMaximaExecucao, Integer duracaoProva);
	
	Optional<ExecucaoProva> recuperarProva(String voucher);
	
	Prova iniciarExecucaoProva(String voucher);
	
	void finalizarExecucaoProva(String voucher);
	

}
