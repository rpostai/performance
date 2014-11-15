package com.rp.performance.repository;

import java.util.List;

public interface Repository<T> {
	
	void salvar(T obj);

	void excluir(Long id);

	T get(Long id);
	
	List<T> getTodos();
}
