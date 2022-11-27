package de.vagtsi.examples.javagenerics.bridgemethods;

public class Entity {
	private final String id;
	
	public Entity(String id) {
		this.id = id;
	}
	
	public String id() {
		return id;
	}
}
