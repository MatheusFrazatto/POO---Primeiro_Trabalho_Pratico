package utilitario;

/**
 * Define os tipos de consulta e suas durações. 
 */
public enum TipoConsulta {
    /**
     * Consulta padrão (60 minutos).
     */
    NORMAL(60),
    /**
     * Consulta de retorno (30 minutos).
     */
    RETORNO(30);

    private final int duracaoEmMinutos;

    /**
     * Construtor do enum.
     *S
     * @param duracaoEmMinutos Duração da consulta em minutos.
     */
    TipoConsulta(int duracaoEmMinutos) {
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    /**
     * Retorna a duração da consulta.
     * @return Duração em minutos.
     */
    public int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }
}