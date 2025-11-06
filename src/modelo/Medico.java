package modelo;

public class Medico extends Funcionario {
    private String crm;
    private String especializacao;

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
