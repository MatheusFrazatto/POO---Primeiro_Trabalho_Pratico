package modelo;

/**
 * Representa uma Secretária. Herda de Funcionário.
 */
public class Secretaria extends Funcionario {
    /**
     * Cria uma nova secretária.
     *
     * @param id O ID.
     * @param nome O nome.
     * @param cpf O CPF.
     * @param salario O salário.
     */
    public Secretaria(int id, String nome, String cpf, float salario) {
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setSalario(salario);
    }
}