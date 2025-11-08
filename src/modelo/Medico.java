package modelo;

/**
 * Representa um Médico. Herda de Funcionário.
 * Adiciona CRM e especialização.
 */
public class Medico extends Funcionario {
    private String crm;
    private String especializacao;

    /**
     * Cria um novo médico.
     *
     * @param id O ID.
     * @param nome O nome.
     * @param cpf O CPF.
     * @param salario O salário.
     * @param crm O CRM.
     * @param especializacao A especialização.
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
}