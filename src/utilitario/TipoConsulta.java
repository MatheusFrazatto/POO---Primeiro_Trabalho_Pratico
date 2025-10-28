package utilitario;

public enum TipoConsulta {
    NORMAL(60),
    RETORNO(30);

    private final int duracaoEmMinutos;

    TipoConsulta(int duracaoEmMinutos) {
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    public int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }
}
