package br.com.dio.model;

public enum GameStatusEnum {

    NON_STARTED("naõ iniciado"),
    INCOMPLET("incompleto"),
    COMPLETE("completado");

    private String label;

    GameStatusEnum(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
