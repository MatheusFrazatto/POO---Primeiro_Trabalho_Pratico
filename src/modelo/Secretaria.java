package modelo;

/**
 * Representa a entidade Secretária (ou secretário) no sistema.
 * Esta classe herda de {@link Funcionario} e representa um usuário
 * com permissões administrativas, como agendamento de consultas
 * e gerenciamento de pacientes.
 *
 */
public class Secretaria extends Funcionario {
    /**
     * Construtor para criar um novo objeto Secretaria.
     *
     * @param id O ID único do funcionário.
     * @param nome O nome completo.
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