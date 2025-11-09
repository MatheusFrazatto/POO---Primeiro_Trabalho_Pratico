package modelo;

/**
 * Classe abstrata base que define os atributos e comportamentos comuns
 * a todos os funcionários da clínica.
 */
public abstract class Funcionario {
    private int id;
    private String nome;
    private String cpf;
    private float salario;

    /**
     * Construtor padrão da classe Funcionario.
     */
    public Funcionario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }
}