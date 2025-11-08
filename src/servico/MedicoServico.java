package servico;

import modelo.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicoServico {
    private PacienteServico pacienteServico;

    public MedicoServico(PacienteServico pacienteServico) {
        this.pacienteServico = pacienteServico;
    }

    public boolean atualizarDadosAdicionais(int idPaciente, boolean fuma, boolean bebe, boolean colesterol, boolean diabetes, String doencasCardiacas, String cirurgias, String alergias) {
        Paciente paciente = pacienteServico.buscarPacientePorId(idPaciente);
        if (paciente != null) {
            DadosAdicionais dadosAdicionais = paciente.getDadosAdicionais();
            dadosAdicionais.setFuma(fuma);
            dadosAdicionais.setBebe(bebe);
            dadosAdicionais.setColesterol(colesterol);
            dadosAdicionais.setDiabete(diabetes);
            dadosAdicionais.setDoencasCardiacas(doencasCardiacas);
            dadosAdicionais.setCirurgias(cirurgias);
            dadosAdicionais.setAlergias(alergias);
            return true;
        }
        return false;
    }

    public boolean limparDadosAdicionais(int idPaciente) {
        Paciente paciente = pacienteServico.buscarPacientePorId(idPaciente);
        if (paciente != null) {
            paciente.setDadosAdicionais(new DadosAdicionais());
            return true;
        }
        return false;
    }

    public boolean cadastrarProntuario(int idPaciente, Medico medico, String sintomas, String diagnostico, String prescricao) {
        Paciente paciente = pacienteServico.buscarPacientePorId(idPaciente);
        if (paciente != null) {
            Prontuario novoProntuario = new Prontuario(0, LocalDate.now(), sintomas, diagnostico, prescricao, medico);
            paciente.adicionarProntuario(novoProntuario);
            return true;
        }
        return false;
    }

    public boolean atualizarProntuario(int idPaciente, int idProntuario, String sintomas, String diagnostico, String prescricao) {
        Paciente paciente = pacienteServico.buscarPacientePorId(idPaciente);
        if (paciente != null) {
            Prontuario prontuario = paciente.buscarProntuarioPorId(idProntuario);
            if (prontuario != null) {
                prontuario.setSintomas(sintomas);
                prontuario.setDiagnostico(diagnostico);
                prontuario.setPrescricao(prescricao);
                return true;
            }
        }
        return false;
    }

    public boolean removerProntuario(int idPaciente, int idProntuario) {
        Paciente paciente = pacienteServico.buscarPacientePorId(idPaciente);
        if (paciente != null) {
            return paciente.removerProntuarioPorId(idProntuario);
        }
        return false;
    }

    public String gerarReceita(int idPaciente, Medico medico, String prescricao) {
        Paciente paciente = pacienteServico.buscarPacientePorId(idPaciente);
        if (paciente == null) {
            return "ERRO: Paciente não encontrado";
        }
        return String.format("--- Receita Médica ---\n" +
                        "Paciente: %s\n" +
                        "CPF: %s\n" +
                        "Prescrição: %s\n" +
                        "Data: %s\n" +
                        "Ass: %s (CRM: %s)",
                paciente.getNome(), paciente.getCpf(), prescricao, LocalDate.now(), medico.getNome(), medico.getCrm());
    }

    public String gerarAtestado(int idPaciente, Medico medico, int diasAfastamento) {
        Paciente paciente = pacienteServico.buscarPacientePorId(idPaciente);
        if (paciente == null) {
            return "ERRO: Paciente não encontrado";
        }
        return String.format("--- Atestado Médico ---\n" +
                        "Atesto para os devido fins que paciente %s, portador(a) do CPF %s, necessita de %d dias(s) de afastamento.\n\n" +
                        "Data: %s\n" +
                        "Ass: %s (CRM: %s))",
                paciente.getNome(), paciente.getCpf(), diasAfastamento, LocalDate.now(), medico.getNome(), medico.getCrm());
    }

    public String gerarDeclaracaoAcompanhamento(int idPaciente, Medico medico, int diasAfastamento, String nomeAcompanhante) {
        Paciente paciente = pacienteServico.buscarPacientePorId(idPaciente);
        if (paciente == null) {
            return "ERRO: Paciente não encontrado";
        }
        return String.format("--- Atestado Médico---\n" +
                        "Declaro para os devido fins que o(a) Sr(a) %s, esteve nesta unidade hospitalar no dia %d, acompanhando o(a) paciente %s.\n\n" +
                        "Data: %s\n" +
                        "Ass: %s (CRM: %s)",
                nomeAcompanhante, LocalDate.now(), paciente.getNome(), LocalDate.now(), medico.getNome(), medico.getCrm());
    }

    public List<Paciente> getClientesAtendidosMes(int idMedico, int mes, int ano, List<Consulta> todasConsultas) {
        return new ArrayList<>();
    }


}
