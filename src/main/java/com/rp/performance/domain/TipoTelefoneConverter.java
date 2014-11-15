package com.rp.performance.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TipoTelefoneConverter implements
		AttributeConverter<TipoTelefone, String> {

	@Override
	public String convertToDatabaseColumn(TipoTelefone tipoTelefone) {
		return tipoTelefone.getSigla();
	}

	@Override
	public TipoTelefone convertToEntityAttribute(String sigla) {
		return TipoTelefone.getTipoTelefone(sigla).get();
	}
}
