package servico;

import modelo.*;
import utilitario.TipoConsulta;
import utilitario.TipoConvenio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.List;

/**
 * Serviço responsável por gerenciar as operações de uma Secretária no sistema, agindo como uma fachada
 * para as funcionalidades de PacienteServico e ConsultaServico, além de gerar relatórios de consultas.
 */
public class SecretariaServico {
    private PacienteServico pacienteServico;
    private ConsultaServico consultaServico;

    /**
     * Construtor para o SecretariaServico que recebe as dependências.
     *
     * @param pacienteServico O serviço de pacientes.
     * @param consultaServico O serviço de consultas.
     */
    public SecretariaServico(PacienteServico pacienteServico, ConsultaServico consultaServico) {
        this.pacienteServico = pacienteServico;
        this.consultaServico = consultaServico;
    }

    /**
     * Delega o cadastro de um novo paciente para o PacienteServico.
     *
     * @param nome           O nome completo do paciente.
     * @param cpf            O CPF do paciente.
     * @param dataNascimento A data de nascimento do paciente.
     * @param endereco       O endereço do paciente.
     * @param contato        As informações de contato do paciente.
     * @param tipoConvenio   O tipo de convênio (plano de saúde ou particular).
     * @return O objeto Paciente recém-cadastrado.
     */
    public Paciente cadastrarPaciente(String nome, String cpf, LocalDate dataNascimento, Endereco endereco, Contato contato, TipoConvenio tipoConvenio) {
        return this.pacienteServico.cadastrarPaciente(nome, cpf, dataNascimento, endereco, contato, tipoConvenio);
    }

    /**
     * Delega a atualização de um paciente para o PacienteServico.
     *
     * @param idPaciente   O ID do paciente a ser atualizado.
     * @param nome         O novo nome.
     * @param endereco     O novo endereço.
     * @param contato      As novas informações de contato.
     * @param tipoConvenio O novo tipo de convênio.
     * @return {@code true} se o paciente foi encontrado e atualizado, {@code false} caso contrário.
     */
    public Boolean atualizarPaciente(int idPaciente, String nome, Endereco endereco, Contato contato, TipoConvenio tipoConvenio) {
        return this.pacienteServico.atualizarPaciente(idPaciente, nome, endereco, contato, tipoConvenio);
    }

    /**
     * Delega a remoção de um paciente para o PacienteServico.
     *
     * @param idPaciente O ID do paciente a ser removido.
     * @return {@code true} se o paciente foi encontrado e removido, {@code false} caso contrário.
     */
    public Boolean removerPaciente(int idPaciente) {
        return this.pacienteServico.removerPaciente(idPaciente);
    }

    /**
     * Delega o cadastro de uma nova consulta para o ConsultaServico.
     *
     * @param dataHora A data e hora da consulta.
     * @param medico   O médico.
     * @param paciente O paciente.
     * @param tipo     O tipo de consulta.
     * @return O objeto Consulta recém-cadastrado.
     */
    public Consulta cadastrarConsulta(LocalDateTime dataHora, Medico medico, Paciente paciente, TipoConsulta tipo) {
        return this.consultaServico.cadastrarConsulta(dataHora, medico, paciente, tipo);
    }

    /**
     * Delega a atualização de uma consulta para o ConsultaServico.
     *
     * @param idConsulta O ID da consulta a ser atualizada.
     * @param dataHora   A nova data e hora.
     * @param medico     O novo médico.
     * @param paciente   O novo paciente.
     * @param tipo       O novo tipo de consulta.
     * @return {@code true} se a consulta foi encontrada e atualizada, {@code false} caso contrário.
     */
    public Boolean atualizarConsulta(int idConsulta, LocalDateTime dataHora, Medico medico, Paciente paciente, TipoConsulta tipo) {
        return this.consultaServico.atualizarConsulta(idConsulta, dataHora, medico, paciente, tipo);
    }

    /**
     * Delega a remoção de uma consulta para o ConsultaServico.
     *
     * @param idConsulta O ID da consulta a ser removida.
     * @return {@code true} se a consulta foi encontrada e removida, {@code false} caso contrário.
     */
    public Boolean removerConsulta(int idConsulta) {
        return this.consultaServico.removerConsulta(idConsulta);
    }

    /**
     * Gera um relatório de consultas agendadas para o dia seguinte (diaDeHoje + 1),
     * filtrando pelos pacientes que possuem o tipo de contato especificado (EMAIL ou TELEFONE).
     *
     * @param diaDeHoje     A data e hora de referência para calcular o dia seguinte.
     * @param filtroContato O tipo de contato a ser filtrado ("EMAIL" ou "TELEFONE").
     * @return Uma lista de objetos Consulta que atendem ao critério de data e filtro de contato.
     */
    public List<Consulta> gerarRelatorioConsultas(LocalDateTime diaDeHoje, String filtroContato) {
        LocalDate dataDiaSeguinte = diaDeHoje.toLocalDate().plusDays(1);

        List<Consulta> listaConsultas = this.consultaServico.getListaConsultas();
        ListIterator<Consulta> it = listaConsultas.listIterator();

        List<Consulta> relatorio = new ArrayList<>();
        while (it.hasNext()) {
            Consulta consulta = it.next();

            LocalDate dataDaConsulta = consulta.getDataHora().toLocalDate();

            if (dataDaConsulta.isEqual(dataDiaSeguinte)) {
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
