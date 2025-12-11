package modelo;

/**
 * Representa a entidade Médico dentro do sistema.
 * Esta classe herda de {@link Funcionario}, aproveitando os campos
 * comuns (ID, nome, CPF, salário), e adiciona atributos específicos
 * da profissão médica: o CRM e sua
 * especialização.
 */
public class Medico extends Funcionario {
    private String crm;
    private String especializacao;

    /**
     * Construtor para criar um novo objeto Medico.
     *
     * @param id             O ID único do funcionário.
     * @param nome           O nome completo do médico.
     * @param cpf            O CPF do médico.
     * @param salario        O salário do médico.
     * @param crm            O registro no Conselho Regional de Medicina (ex: "CRM/PR 12345").
     * @param especializacao A área de especialização (ex: "Dermatologia").
     */
    public Medico(int id, String nome, String cpf, float salario, String crm, String especializacao) {
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setSalario(salario);
        this.setCrm(crm);
        this.setEspecializacao(especializacao);
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }
    
    @Override
    public String toString() {
        // Retorna o Nome e a Especialidade para ficar bonito na lista
        return this.nome + " (" + this.especializacao + ")";
    }
}