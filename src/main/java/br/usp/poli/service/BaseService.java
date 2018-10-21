package br.usp.poli.service;

import java.util.List;

public interface BaseService<T> {
	
	public abstract Long save(T t);
	
	public abstract List<T> readAll();
	
	public abstract T readById(Long id);
	
	public abstract void delete(Long id);
}
