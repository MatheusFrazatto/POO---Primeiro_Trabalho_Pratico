package servico;

import modelo.*;
import utilitario.TipoConsulta;
import utilitario.TipoConvenio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SecretariaServico {
    private List<Paciente> pacientes = new ArrayList<>();
    private List<Medico> medicos = new ArrayList<>();
    private List<Consulta> consultas = new ArrayList<>();

    private int proximoIdPaciente = 1;
    private int proximoIdMedico = 1;
    private int proximoIdConsulta = 1;

    public SecretariaServico() {
    }

    public Paciente cadastrarPaciente(String cpf, String nome, LocalDate dataNascimento, Endereco endereco, Contato contato, TipoConvenio tipoConvenio) {
        DadosAdicionais dadosAdicionais = new DadosAdicionais(false, false, false, false, "", "", "");

        Paciente novoPaciente = new Paciente(
                proximoIdPaciente++,
                cpf,
                nome,
                dataNascimento,
                endereco,
                contato,
                tipoConvenio
        );

        this.pacientes.add(novoPaciente);

        return novoPaciente;
    }

    public boolean atualizarPaciente(int id, Endereco endereco, Contato contato, TipoConvenio tipoConvenio) {
        Paciente paciente = buscarPaciente(id);
        if (paciente != null) {
            paciente.setEndereco(endereco);
            paciente.setContato(contato);
            paciente.setTipoConvenio(tipoConvenio);
            return true;
        }
        return false;
    }

    public boolean removerPaciente(int id) {
        Paciente paciente = buscarPaciente(id);
        if (paciente != null) {
            this.pacientes.remove(paciente);
            return true;
        }
        return false;
    }

    public Paciente buscarPaciente(int id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getId() == id) {
                return paciente;
            }
        }
        return null;
    }

    public Medico buscarMedico(int id) {
        for (Medico medico : medicos) {
            if (medico.getId() == id) {
                return medico;
            }
        }
        return null;
    }

    public Consulta agendarConsulta(LocalDateTime dataHora, int idMedico, int idPaciente, TipoConsulta tipoConsulta) {
        Paciente paciente = buscarPaciente(idPaciente);
        Medico medico = buscarMedico(idMedico);

        if (paciente == null || medico == null) {
            System.out.println("Erro: Paciente ou Médico não encontrado!");
            return null;
        }

        Consulta consulta = new Consulta(dataHora, medico, paciente, tipoConsulta);
        consulta.setId(proximoIdConsulta++);
        this.consultas.add(consulta);

        return consulta;
    }

    public boolean cancelarConsulta(int id) {
        for (Consulta consulta : consultas) {
            if (consulta.getId() == id) {
                consultas.remove(consulta);
                return true;
            }
        }
        return false;
    }

    public List<Consulta> gerarRelatorioConsultas() {
        ...
    }
}
