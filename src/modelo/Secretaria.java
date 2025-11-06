package modelo;

public class Secretaria extends Funcionario {
    public Secretaria(int id, String nome, String cpf, float salario) {
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setSalario(salario);
    }
}
