package de.vagtsi.examples.javagenerics.bridgemethods;

public class NamedEntity extends Entity {
	private final String name;

	public NamedEntity(String id, String name) {
		super(id);
		this.name = name;
	}
	
	public String name() {
		return name;
	}
}
