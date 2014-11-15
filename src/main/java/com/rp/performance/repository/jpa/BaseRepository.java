package com.rp.performance.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.rp.performance.repository.Repository;

public abstract class BaseRepository<T> implements Repository<T> {

	@PersistenceContext(unitName = "performancePU")
	private EntityManager em;

	private Class<T> clazz;

	public BaseRepository(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void salvar(T obj) {
		em.persist(obj);
	}

	public void excluir(Long id) {
		T obj = get(id);
		em.remove(obj);
	}

	public T get(Long id) {
		return em.find(clazz, id);
	}

	public List<T> getTodos() {
		TypedQuery<T> tq = em.createQuery(
				"select obj from " + clazz.getSimpleName() + " obj", clazz);
		return tq.getResultList();
	}

}
