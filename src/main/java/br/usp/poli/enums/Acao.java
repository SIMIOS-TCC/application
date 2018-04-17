package br.usp.poli.enums;

/**
 * @author Juan Leiro
 */
public enum Acao {

    INSERTED("INSERTED"),
    UPDATED("UPDATED"),
    DELETED("deletar");

    private final String name;

    private Acao(String value) {
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