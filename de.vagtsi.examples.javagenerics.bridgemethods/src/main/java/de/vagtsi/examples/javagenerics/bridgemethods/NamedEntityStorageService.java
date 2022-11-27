package de.vagtsi.examples.javagenerics.bridgemethods;

import javax.inject.Inject;

public class NamedEntityStorageService implements EntityStorageService<NamedEntity> {

	@Inject
	@Override
	public void insert(NamedEntity entity) {
		System.out.println("Inserting entity with name " + entity.name());
	}

}
