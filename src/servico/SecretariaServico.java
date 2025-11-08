package servico;

import modelo.*;
import utilitario.TipoConsulta;
import utilitario.TipoConvenio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.List;

public class SecretariaServico {
    private PacienteServico pacienteServico;
    private ConsultaServico consultaServico;

    public SecretariaServico() {
    }


    public SecretariaServico(PacienteServico pacienteServico, ConsultaServico consultaServico) {
        this.pacienteServico = pacienteServico;
        this.consultaServico = consultaServico;
    }

    public Paciente cadastrarPaciente(String nome, String cpf, LocalDate dataNascimento, Endereco endereco, Contato contato, TipoConvenio tipoConvenio) {
        return this.pacienteServico.cadastrarPaciente(nome, cpf, dataNascimento, endereco, contato, tipoConvenio);
    }

    public Boolean atualizarPaciente(int idPaciente, String nome, Endereco endereco, Contato contato, TipoConvenio tipoConvenio) {
        return this.pacienteServico.atualizarPaciente(idPaciente, nome, endereco, contato, tipoConvenio);
    }

    public Boolean removerPaciente(int idPaciente) {
        return this.pacienteServico.removerPaciente(idPaciente);
    }

    public Consulta cadastrarConsulta(LocalDateTime dataHora, Medico medico, Paciente paciente, TipoConsulta tipo) {
        return this.consultaServico.cadastrarConsulta(dataHora, medico, paciente, tipo);
    }

    public Boolean atualizarConsulta(int idConsulta, LocalDateTime dataHora, Medico medico, Paciente paciente, TipoConsulta tipo) {
        return this.consultaServico.atualizarConsulta(idConsulta, dataHora, medico, paciente, tipo);
    }

    public Boolean removerConsulta(int idConsulta) {
        return this.consultaServico.removerConsulta(idConsulta);
    }

    public List<Consulta> gerarRelatorioConsultas(LocalDateTime diaDeHoje, String filtroContato) {
        LocalDateTime diaSeguinte = diaDeHoje.plusDays(1);
        List<Consulta> listaConsultas = this.consultaServico.getListaConsultas();
        ListIterator<Consulta> it = listaConsultas.listIterator();

        List<Consulta> relatorio = new ArrayList<>();
        while (it.hasNext()) {
            Consulta consulta = it.next();

            if (consulta.getDataHora().isEqual(diaSeguinte)) {
                Paciente paciente = consulta.getPaciente();
                Contato contato = paciente.getContato();

                Boolean temEmail = contato.getEmail() != null && !contato.getEmail().isEmpty();
                Boolean temTelefone = contato.getTelefone() != null && !contato.getTelefone().isEmpty();
                switch (filtroContato) {
                    case "EMAIL":
                        if (temEmail) relatorio.add(consulta);
                        break;
                    case "TELEFONE":
                        if (temTelefone) relatorio.add(consulta);
                        break;
                }
            }
        }
        return relatorio;
    }
}
