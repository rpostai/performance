package com.rp.performance.repository.jpa.prova.execucao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;

import com.rp.performance.domain.Candidato;
import com.rp.performance.domain.ExecucaoProva;
import com.rp.performance.domain.Prova;
import com.rp.performance.domain.exceptions.VoucherNaoEncontradoException;
import com.rp.performance.repository.jpa.BaseRepository;
import com.rp.performance.repository.jpa.prova.ProvaRepository;

@Stateless
public class ExecucaoProvaRepositoryBean extends BaseRepository<ExecucaoProva>
		implements ExecucaoProvaRepository {
	
	@EJB
	private ProvaRepository provaRepository;

	public ExecucaoProvaRepositoryBean() {
		super(ExecucaoProva.class);
	}

	@Override
	public String gerarVoucher(Candidato candidato, Prova prova,
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

			return exec.getVoucher();
		}

		return null;
	}

	private Optional<ExecucaoProva> existeVoucherGeradoEmAberto(
			Candidato candidato, Prova prova) {
		TypedQuery<ExecucaoProva> tq = em
				.createQuery(
						"select o from ExecucaoProva o where o.candidato = :candidato and o.prova = :prova and o.dataInicio is null and o.dataConclusao is null",
						ExecucaoProva.class);
		tq.setParameter("prova", prova);
		tq.setParameter("candidato", candidato);
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
	public Prova iniciarExecucaoProva(String voucher) {
		Optional<ExecucaoProva> execucao = recuperarProva(voucher);
		if (execucao.isPresent()) {
			ExecucaoProva exec = execucao.get();
			exec.iniciarExecucao();
			return provaRepository.getProvaComAlternativas(exec.getProva().getId());
		}
		throw new VoucherNaoEncontradoException();
	}

	@Override
	public void finalizarExecucaoProva(String voucher) {
		Optional<ExecucaoProva> execucao = recuperarProva(voucher);
		if (execucao.isPresent()) {
			ExecucaoProva exec = execucao.get();
			exec.finalizarExecucao();
		} else {
			throw new VoucherNaoEncontradoException();
		}
	}
}
