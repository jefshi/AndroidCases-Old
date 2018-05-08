package com.project.tools.piture;

public class Animal {

	private String	name;
	private String	path;

	public Animal(String name, String path) {
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public String getPhoto() {
		return path;
	}
}
