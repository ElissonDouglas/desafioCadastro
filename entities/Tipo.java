package entities;

public enum Tipo {
    GATO(1),
    CACHORRO(2);

    private int tipo;

    Tipo(int tipo) {
        this.tipo = tipo;
    }

    public static Tipo pegarPorNumero(int numero) {
        for (Tipo t : Tipo.values()) {
            if (t.tipo == numero) {
                return t;
            }
        }
        return null; // ou lançar uma exceção
    }
}
