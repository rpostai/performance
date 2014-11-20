package com.rp.performance.domain.prova;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class TipoQuestaoConverter implements
		AttributeConverter<TipoQuestao, String> {

	@Override
	public String convertToDatabaseColumn(TipoQuestao tipo) {
		return tipo.getSigla();
	}

	@Override
	public TipoQuestao convertToEntityAttribute(String sigla) {
		return TipoQuestao.getTipoQuestao(sigla);
	}

}
