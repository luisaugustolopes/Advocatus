package br.com.lopes.controller;

public interface IManagedBean<PK, T> {
	
	public T getByID(PK pk);
	public void save(T entity);
	public void update(T entity);
	public void delete(T entity);	
	
}
