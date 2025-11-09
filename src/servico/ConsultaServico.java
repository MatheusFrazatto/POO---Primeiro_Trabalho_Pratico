package servico;

import modelo.Consulta;
import modelo.Paciente;
import modelo.Medico;
import utilitario.TipoConsulta;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável por gerenciar as operações relacionadas a Consultas.
 * Inclui métodos para cadastrar, atualizar, remover e buscar consultas.
 */
public class ConsultaServico {
    private List<Consulta> listaConsultas = new ArrayList<>();
    private int proximoIdConsulta = 1;

    /**
     * Construtor padrão para o ConsultaServico.
     */
    public ConsultaServico() {
    }

    /**
     * Cadastra uma nova consulta no sistema.
     * Atribui um ID único à nova consulta.
     *
     * @param dataHora A data e hora da consulta.
     * @param medico   O médico que realizará a consulta.
     * @param paciente O paciente agendado para a consulta.
     * @param tipo     O tipo de consulta (normal ou retorno).
     * @return O objeto Consulta recém-cadastrado.
     */
    public Consulta cadastrarConsulta(LocalDateTime dataHora, Medico medico, Paciente paciente, TipoConsulta tipo) {
        Consulta novaConsulta = new Consulta(dataHora, medico, paciente, tipo);
        novaConsulta.setId(proximoIdConsulta++);
        this.listaConsultas.add(novaConsulta);
        return novaConsulta;
    }

    /**
     * Atualiza os dados de uma consulta existente.
     *
     * @param id       O ID da consulta a ser atualizada.
     * @param dataHora A nova data e hora da consulta.
     * @param medico   O novo médico da consulta.
     * @param paciente O novo paciente da consulta.
     * @param tipo     O novo tipo de consulta.
     * @return {@code true} se a consulta foi encontrada e atualizada, {@code false} caso contrário.
     */
    public Boolean atualizarConsulta(int id, LocalDateTime dataHora, Medico medico, Paciente paciente, TipoConsulta tipo) {
        Consulta consulta = buscarConsultaPorId(id);
        if (consulta != null) {
            consulta.setDataHora(dataHora);
            consulta.setMedico(medico);
            consulta.setPaciente(paciente);
            consulta.setTipo(tipo);
            return true;
        }
        return false;
    }

    /**
     * Remove uma consulta do sistema com base no seu ID.
     *
     * @param id O ID da consulta a ser removida.
     * @return {@code true} se a consulta foi encontrada e removida, {@code false} caso contrário.
     */
    public boolean removerConsulta(int id) {
        Consulta consulta = buscarConsultaPorId(id);
        if (consulta != null) {
            this.listaConsultas.remove(consulta);
            return true;
        }
        return false;
    }

    /**
     * Busca uma consulta pelo seu ID.
     *
     * @param id O ID da consulta a ser buscada.
     * @return O objeto Consulta correspondente ao ID, ou {@code null} se não for encontrado.
     */
    public Consulta buscarConsultaPorId(int id) {
        for (Consulta consulta : listaConsultas) {
            if (consulta.getId() == id) {
                return consulta;
            }
        }
        return null;
    }

    /**
     * Retorna a lista completa de consultas cadastradas.
     *
     * @return Uma lista de objetos Consulta.
     */
    public List<Consulta> getListaConsultas() {
        return listaConsultas;
    }
}
