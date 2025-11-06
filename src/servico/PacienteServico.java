package servico;

import modelo.*;
import utilitario.TipoConvenio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteServico {
    private List<Paciente> listaPacientes = new ArrayList<>();
    private int proximoIdPaciente = 1;

    public PacienteServico() {
    }

    public Paciente cadastrarPaciente(String nome, String cpf, LocalDate dataNascimento, Endereco endereco, Contato contato, TipoConvenio tipoConvenio) {
        Paciente novoPaciente = new Paciente(0, nome, cpf, dataNascimento, endereco, contato, tipoConvenio);
        novoPaciente.setId(proximoIdPaciente++);
        this.listaPacientes.add(novoPaciente);
        return novoPaciente;
    }

    public boolean atualizarPaciente(int id, String nome, Endereco endereco, Contato contato, TipoConvenio tipoConvenio) {
        Paciente paciente = buscarPacientePorId(id);
        if (paciente != null) {
            paciente.setNome(nome);
            paciente.setEndereco(endereco);
            paciente.setContato(contato);
            paciente.setTipoConvenio(tipoConvenio);
            return true;
        }
        return false;
    }

    public Paciente buscarPacientePorId(int id) {
        for (Paciente paciente : listaPacientes) {
            if (paciente.getId() == id) {
                return paciente;
            }
        }
        return null;
    }

    public boolean removerPaciente(int id) {
        Paciente paciente = buscarPacientePorId(id);
        if (paciente != null) {
            this.listaPacientes.remove(paciente);
            return true;
        }
        return false;
    }
    public List<Paciente> getListaPacientes() {
        return this.listaPacientes;
    }
}
