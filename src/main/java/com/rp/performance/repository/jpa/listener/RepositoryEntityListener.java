package com.rp.performance.repository.jpa.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.rp.performance.domain.BaseEntity;
import com.rp.performance.domain.DateUtils;

public class RepositoryEntityListener {

	@PrePersist
	public void prePersist(BaseEntity entity) {
		entity.setDataCadastro(DateUtils.getDate());
	}
	
	@PreUpdate
	public void preUpdate(BaseEntity entity) {
		entity.setDataCadastro(DateUtils.getDate());
	}
}
