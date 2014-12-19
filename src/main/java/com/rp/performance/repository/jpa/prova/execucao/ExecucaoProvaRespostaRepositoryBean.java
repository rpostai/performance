package com.rp.performance.repository.jpa.prova.execucao;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.rp.performance.domain.AlternativaQuestao;
import com.rp.performance.domain.ExecucaoProva;
import com.rp.performance.domain.ExecucaoProvaResposta;
import com.rp.performance.domain.Prova;
import com.rp.performance.domain.Questao;
import com.rp.performance.domain.exceptions.TempoDuracaoProvaAtingidoException;
import com.rp.performance.domain.exceptions.VoucherNaoEncontradoException;
import com.rp.performance.repository.jpa.BaseRepository;
import com.rp.performance.repository.jpa.prova.QuestaoRepository;

@Stateless
public class ExecucaoProvaRespostaRepositoryBean extends
		BaseRepository<ExecucaoProvaResposta> implements
		ExecucaoProvaRespostaRepository {

	@EJB
	private QuestaoRepository questaoRepository;

	@EJB
	private ExecucaoProvaRepository execucaoProvaRepository;
	
	public ExecucaoProvaRespostaRepositoryBean() {
		super(ExecucaoProvaResposta.class);
	}

	@Override
	public void responder(String voucher, Long questaoId,
			List<Long> alternativa, String resposta) {

		Optional<ExecucaoProva> exec = execucaoProvaRepository
				.recuperarProva(voucher);
		if (!exec.isPresent()) {
			throw new VoucherNaoEncontradoException();
		} else {
			
			Prova prova = exec.get().getProva();
			Date dataInicio = exec.get().getDataInicio();
			
			if (prova.isTempoLimiteValido(dataInicio)) {
				Optional<ExecucaoProvaResposta> respostaSalva = getExecucaoProvaResposta(
						exec.get().getProva().getId(), questaoId);
				if (!respostaSalva.isPresent()) {
					ExecucaoProvaResposta epr = new ExecucaoProvaResposta();
					epr.setExecucaoProva(exec.get());
					
					Questao q = questaoRepository.get(questaoId);
					epr.setQuestao(q);
					
					epr.setRespostaAberta(resposta);
					
					List<AlternativaQuestao> alternativas = q.getAlternativas().stream().filter(questaoAlternativa -> {
						return alternativa.stream().filter(id -> {
							return questaoAlternativa.getId().equals(id);
						}).findFirst().isPresent();
					}).collect(Collectors.toList());
					
					epr.setRespostas(alternativas);
					
					salvar(epr);
				}	
			} else {
				throw new TempoDuracaoProvaAtingidoException();
			}
		}
	}
	
	private Optional<ExecucaoProvaResposta> getExecucaoProvaResposta(
			Long execucaoProvaId, Long questaoId) {
		TypedQuery<ExecucaoProvaResposta> tq = em
				.createQuery(
						"select o from ExecucaoProvaResposta o where o.execucaoProva.id = :prova and o.questao.id = :questao",
						ExecucaoProvaResposta.class);
		tq.setParameter("prova", execucaoProvaId);
		tq.setParameter("questao", questaoId);
		try {
			return Optional.of(tq.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

}
