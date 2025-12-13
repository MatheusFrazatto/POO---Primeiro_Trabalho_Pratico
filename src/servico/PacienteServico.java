package servico;

import modelo.Contato;
import modelo.Endereco;
import modelo.Paciente;
import utilitario.JPAUtil;
import utilitario.TipoConvenio;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

/**
 * Serviço responsável por gerenciar as operações relacionadas a Pacientes.
 * Inclui métodos para cadastrar, atualizar, remover e buscar pacientes,
 * utilizando JPA para persistência de dados.
 */
public class PacienteServico {

    /**
     * Cadastra um novo paciente no sistema.
     *
     * @param nome           O nome completo do paciente.
     * @param cpf            O CPF do paciente.
     * @param dataNascimento A data de nascimento do paciente.
     * @param endereco       O endereço do paciente.
     * @param contato        As informações de contato do paciente.
     * @param tipoConvenio   O tipo de convênio (plano de saúde ou particular).
     * @return O objeto Paciente recém-cadastrado e persistido.
     */
    public Paciente cadastrarPaciente(String nome, String cpf, LocalDate dataNascimento, Endereco endereco, Contato contato, TipoConvenio tipoConvenio) {
        Paciente novoPaciente = new Paciente(0, nome, cpf, dataNascimento, endereco, contato, tipoConvenio);
        
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(novoPaciente);
            em.getTransaction().commit();
            return novoPaciente;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Atualiza os dados cadastrais de um paciente existente.
     *
     * @param id           O ID do paciente a ser atualizado.
     * @param nome         O novo nome.
     * @param endereco     O novo endereço.
     * @param contato      As novas informações de contato.
     * @param tipoConvenio O novo tipo de convênio.
     * @return {@code true} se o paciente foi encontrado e atualizado, {@code false} caso contrário.
     */
    public boolean atualizarPaciente(int id, String nome, Endereco endereco, Contato contato, TipoConvenio tipoConvenio) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Paciente paciente = em.find(Paciente.class, id);
            if (paciente != null) {
                em.getTransaction().begin();
                paciente.setNome(nome);
                paciente.setEndereco(endereco);
                paciente.setContato(contato);
                paciente.setTipoConvenio(tipoConvenio);
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
     * Busca um paciente pelo seu ID.
     *
     * @param id O ID do paciente a ser buscado.
     * @return O objeto Paciente correspondente ao ID, ou {@code null} se não for encontrado.
     */
    public Paciente buscarPacientePorId(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Paciente.class, id);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Remove um paciente do sistema com base no seu ID.
     *
     * @param id O ID do paciente a ser removido.
     * @return {@code true} se o paciente foi encontrado e removido, {@code false} caso contrário.
     */
    public boolean removerPaciente(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Paciente paciente = em.find(Paciente.class, id);
            if (paciente != null) {
                em.getTransaction().begin();
                em.remove(paciente);
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
     * Retorna a lista completa de pacientes cadastrados.
     *
     * @return Uma lista de objetos Paciente.
     */
    public List<Paciente> getListaPacientes() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Paciente p", Paciente.class).getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}