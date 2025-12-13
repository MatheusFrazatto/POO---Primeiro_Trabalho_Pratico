package servico;

import modelo.Consulta;
import modelo.Paciente;
import modelo.Medico;
import utilitario.JPAUtil;
import utilitario.TipoConsulta;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Serviço responsável por gerenciar as operações relacionadas a Consultas,
 * utilizando JPA para persistência de dados.
 */
public class ConsultaServico {

    /**
     * Cadastra uma nova consulta no sistema.
     *
     * @param dataHora A data e hora da consulta.
     * @param medico   O médico que realizará a consulta.
     * @param paciente O paciente agendado para a consulta.
     * @param tipo     O tipo de consulta (normal ou retorno).
     * @return O objeto Consulta recém-cadastrado e persistido.
     */
    public Consulta cadastrarConsulta(LocalDateTime dataHora, Medico medico, Paciente paciente, TipoConsulta tipo) {
        Consulta novaConsulta = new Consulta(dataHora, medico, paciente, tipo);
        
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(novaConsulta);
            em.getTransaction().commit();
            return novaConsulta;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
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
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Consulta consulta = em.find(Consulta.class, id);
            if (consulta != null) {
                em.getTransaction().begin();
                consulta.setDataHora(dataHora);
                consulta.setMedico(medico);
                consulta.setPaciente(paciente);
                consulta.setTipo(tipo);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Remove uma consulta do sistema com base no seu ID.
     *
     * @param id O ID da consulta a ser removida.
     * @return {@code true} se a consulta foi encontrada e removida, {@code false} caso contrário.
     */
    public boolean removerConsulta(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Consulta consulta = em.find(Consulta.class, id);
            if (consulta != null) {
                em.getTransaction().begin();
                em.remove(consulta);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // Logar a exceção pode ser útil aqui
            return false;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Busca uma consulta pelo seu ID.
     *
     * @param id O ID da consulta a ser buscada.
     * @return O objeto Consulta correspondente ao ID, ou {@code null} se não for encontrado.
     */
    public Consulta buscarConsultaPorId(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Consulta.class, id);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Retorna a lista completa de consultas cadastradas.
     *
     * @return Uma lista de objetos Consulta.
     */
    public List<Consulta> getListaConsultas() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Consulta c", Consulta.class).getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}