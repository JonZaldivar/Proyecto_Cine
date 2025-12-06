package proyectoCine.domain;

public enum Descuento {
    SIN_DESCUENTO(""), 
    DIEZ("10"), 
    VEINTICINCO("25"), 
    CINCUENTA("50");

    private final String valor;

    Descuento(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}

