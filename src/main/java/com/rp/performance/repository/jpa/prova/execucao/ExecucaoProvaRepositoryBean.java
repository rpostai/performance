package com.rp.performance.repository.jpa.prova.execucao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;

import com.rp.performance.domain.prova.Prova;
import com.rp.performance.domain.prova.execucao.Candidato;
import com.rp.performance.domain.prova.execucao.ExecucaoProva;
import com.rp.performance.repository.jpa.BaseRepository;

@Stateless
public class ExecucaoProvaRepositoryBean extends BaseRepository<ExecucaoProva>
		implements ExecucaoProvaRepository {

	public ExecucaoProvaRepositoryBean() {
		super(ExecucaoProva.class);
	}

	@Override
	public void gerarVoucher(Candidato candidato, Prova prova,
			Date dataAbertura, Date dataMaximaExecucao, Integer duracaoProva) {
		Optional<ExecucaoProva> voucher = existeVoucherGeradoEmAberto(
				candidato, prova);
		if (!voucher.isPresent()) {

			ExecucaoProva exec = new ExecucaoProva();
			exec.setCandidato(candidato);
			exec.setProva(prova);
			exec.setDataAbertura(dataAbertura);
			exec.setDataValidade(dataMaximaExecucao);

			em.persist(exec);

		}
	}

	private Optional<ExecucaoProva> existeVoucherGeradoEmAberto(
			Candidato candidato, Prova prova) {
		TypedQuery<ExecucaoProva> tq = em
				.createQuery(
						"select o from ExecucaoProva o where o.candidato = :candidato and o.prova = :prova and o.dataInicio is null and o.dataConclusao is null",
						ExecucaoProva.class);
		tq.setParameter("prova", prova);
		tq.setParameter("candidato", prova);
		List<ExecucaoProva> vouchers = tq.getResultList();
		if (CollectionUtils.isNotEmpty(vouchers)) {
			return Optional.of(vouchers.get(0));
		}
		return Optional.empty();
	}

	@Override
	public void salvar(ExecucaoProva obj) {
		throw new IllegalAccessError("Este método nao deve ser executado");
	}

	@Override
	public Optional<ExecucaoProva> recuperarProva(String voucher) {
		TypedQuery<ExecucaoProva> tq = em.createQuery(
				"select o from ExecucaoProva o where o.voucher = :voucher",
				ExecucaoProva.class);
		tq.setParameter("voucher", voucher);
		List<ExecucaoProva> vouchers = tq.getResultList();
		if (CollectionUtils.isNotEmpty(vouchers)) {
			return Optional.of(vouchers.get(0));
		}
		return Optional.empty();
	}

	@Override
	public void iniciarProva(String voucher) {
		Optional<ExecucaoProva> execucao = recuperarProva(voucher);
		if (execucao.isPresent()) {
			execucao.get().setDataInicio(Calendar.getInstance().getTime());
		} else {
			throw new RuntimeException("Voucher não encontrado");
		}
	}

	@Override
	public void finalizarProva(String voucher) {
		Optional<ExecucaoProva> execucao = recuperarProva(voucher);
		if (execucao.isPresent()) {
			execucao.get().setDataConclusao(Calendar.getInstance().getTime());
		} else {
			throw new RuntimeException("Voucher não encontrado");
		}
	}

}
