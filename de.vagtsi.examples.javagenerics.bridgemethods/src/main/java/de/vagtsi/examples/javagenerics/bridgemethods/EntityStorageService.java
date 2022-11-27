package de.vagtsi.examples.javagenerics.bridgemethods;

public interface EntityStorageService<T extends Entity> {
	void insert(T entity);
}
