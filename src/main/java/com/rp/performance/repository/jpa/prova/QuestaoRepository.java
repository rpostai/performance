package com.rp.performance.repository.jpa.prova;

import java.util.List;
import java.util.Set;

import com.rp.performance.domain.prova.AreaConhecimento;
import com.rp.performance.domain.prova.Assunto;
import com.rp.performance.domain.prova.Questao;
import com.rp.performance.repository.Repository;

public interface QuestaoRepository extends Repository<Questao>{

	public List<Questao> pesquisarQuestoes(AreaConhecimento areaConhecimento, Set<Assunto> assuntos);
}
