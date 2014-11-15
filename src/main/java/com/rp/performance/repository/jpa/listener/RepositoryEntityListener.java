package com.rp.performance.repository.jpa.listener;

import java.util.Calendar;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.rp.performance.domain.BaseEntity;

public class RepositoryEntityListener {

	@PrePersist
	public void prePersist(BaseEntity entity) {
		entity.setDataCadastro(Calendar.getInstance().getTime());
	}
	
	@PreUpdate
	public void preUpdate(BaseEntity entity) {
		entity.setDataCadastro(Calendar.getInstance().getTime());
	}
}
