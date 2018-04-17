package br.usp.poli.enums;

/**
 * @author Juan Leiro
 */
public enum TelaCadastro {

    USUARIO("USUARIO"),
    SIMIO("SIMIO");

    private final String name;

    private TelaCadastro(String value) {
        this.name = value;
    }

    public String value() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}