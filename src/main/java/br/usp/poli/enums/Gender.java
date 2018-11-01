package br.usp.poli.enums;

public enum Gender {

    MALE("Male"),
    FEMALE("Female"),
    NA("N/A");

    private final String description;

    private Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
		return name();
	}
}