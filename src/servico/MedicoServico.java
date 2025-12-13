    package servico;

import modelo.*;
import utilitario.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável por gerenciar as operações de um Médico no sistema, incluindo atualização de dados
 * adicionais do paciente, gestão de prontuários e geração de documentos médicos (Receita, Atestado, Declaração).
 */
public class MedicoServico {
    private PacienteServico pacienteServico;

    /**
     * Construtor para o MedicoServico.
     *
     * @param pacienteServico O serviço de pacientes necessário para buscar e manipular dados de pacientes.
     */
    public MedicoServico(PacienteServico pacienteServico) {
        this.pacienteServico = pacienteServico;
    }

    /**
     * Salva ou atualiza um médico no banco de dados.
     * Se o médico é novo (ID 0), ele é persistido. Caso contrário, é atualizado.
     *
     * @param medico O objeto Medico a ser salvo.
     */
    public void salvar(Medico medico) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (medico.getId() == 0) {
                em.persist(medico);
            } else {
                em.merge(medico);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Busca um médico pelo seu ID.
     *
     * @param id O ID do médico.
     * @return O objeto Medico encontrado ou null se não existir.
     */
    public Medico buscarPorId(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Medico.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista com todos os médicos cadastrados.
     *
     * @return Uma lista de objetos Medico.
     */
    public List<Medico> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Medico> query = em.createQuery("FROM Medico", Medico.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Remove um médico do banco de dados pelo seu ID.
     *
     * @param id O ID do médico a ser removido.
     */
    public void remover(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Medico medico = em.find(Medico.class, id);
            if (medico != null) {
                em.remove(medico);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Atualiza os dados adicionais de saúde de um paciente.
     *
     * @param idPaciente       O ID do paciente cujos dados adicionais serão atualizados.
     * @param fuma             Indica se o paciente fuma.
     * @param bebe             Indica se o paciente bebe.
     * @param colesterol       Indica se o paciente tem colesterol alto.
     * @param diabetes         Indica se o paciente tem diabetes.
     * @param doencasCardiacas Descrição das doenças cardíacas do paciente.
     * @param cirurgias        Descrição das cirurgias realizadas pelo paciente.
     * @param alergias         Descrição das alergias do paciente.
     * @return {@code true} se o paciente foi encontrado e os dados atualizados, {@code false} caso contrário.
     */
    public boolean atualizarDadosAdicionais(int idPaciente, boolean fuma, boolean bebe, boolean colesterol, boolean diabetes, String doencasCardiacas, String cirurgias, String alergias) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // 2. Busca o paciente (agora gerenciado por esta conexão)
            Paciente paciente = em.find(Paciente.class, idPaciente);
            
            if (paciente != null) {
                em.getTransaction().begin(); // Inicia a gravação
                
                DadosAdicionais dadosAdicionais = paciente.getDadosAdicionais();
                
                // Prevenção: Se não existir os dados ainda, cria um novo objeto
                if (dadosAdicionais == null) {
                    dadosAdicionais = new DadosAdicionais();
                    paciente.setDadosAdicionais(dadosAdicionais);
                }
                
                // Atualiza os valores
                dadosAdicionais.setFuma(fuma);
                dadosAdicionais.setBebe(bebe);
                dadosAdicionais.setColesterol(colesterol);
                dadosAdicionais.setDiabete(diabetes);
                dadosAdicionais.setDoencasCardiacas(doencasCardiacas);
                dadosAdicionais.setCirurgias(cirurgias);
                dadosAdicionais.setAlergias(alergias);
                
                // 3. O PULO DO GATO: Salva (merge) e Confirma (commit)
                em.merge(paciente);
                em.getTransaction().commit(); 
                
                return true;
            }
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Limpa (reseta) todos os dados adicionais de um paciente, criando um novo objeto DadosAdicionais.
     *
     * @param idPaciente O ID do paciente cujos dados adicionais serão limpos.
     * @return {@code true} se o paciente foi encontrado e os dados limpos, {@code false} caso contrário.
     */
    public boolean limparDadosAdicionais(int idPaciente) {
        Paciente paciente = pacienteServico.buscarPacientePorId(idPaciente);
        if (paciente != null) {
            paciente.setDadosAdicionais(new DadosAdicionais());
            return true;
        }
        return false;
    }

    /**
     * Cadastra um novo prontuário para um paciente.
     * O prontuário é criado com a data atual.
     *
     * @param idPaciente  O ID do paciente.
     * @param medico      O médico responsável pelo prontuário.
     * @param sintomas    Descrição dos sintomas do paciente.
     * @param diagnostico O diagnóstico estabelecido.
     * @param prescricao  A prescrição médica.
     * @return {@code true} se o paciente foi encontrado e o prontuário cadastrado, {@code false} caso contrário.
     */
    public boolean cadastrarProntuario(int idPaciente, Medico medico, String sintomas, String diagnostico, String prescricao) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
  
            Paciente paciente = em.find(Paciente.class, idPaciente);

            if (paciente != null) {
                em.getTransaction().begin();

                Prontuario novoProntuario = new Prontuario(0, LocalDate.now(), sintomas, diagnostico, prescricao, medico);
                novoProntuario.setPaciente(paciente);

                paciente.adicionarProntuario(novoProntuario);

                em.merge(paciente);

                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Atualiza os detalhes de um prontuário específico de um paciente.
     *
     * @param idPaciente   O ID do paciente.
     * @param idProntuario O ID do prontuário a ser atualizado.
     * @param sintomas     Os novos sintomas.
     * @param diagnostico  O novo diagnóstico.
     * @param prescricao   A nova prescrição.
     * @return {@code true} se o paciente e o prontuário foram encontrados e atualizados, {@code false} caso contrário.
     */
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

    /**
     * Remove um prontuário específico de um paciente.
     *
     * @param idPaciente   O ID do paciente.
     * @param idProntuario O ID do prontuário a ser removido.
     * @return {@code true} se o paciente foi encontrado e o prontuário removido, {@code false} caso contrário.
     */
    public boolean removerProntuario(int idPaciente, int idProntuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Paciente paciente = em.find(Paciente.class, idPaciente);

            if (paciente != null) {
                em.getTransaction().begin();

                boolean removeu = paciente.removerProntuarioPorId(idProntuario);

                if (removeu) {
                    em.merge(paciente); 
                    em.getTransaction().commit(); 
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Gera uma receita médica formatada para um paciente.
     *
     * @param idPaciente O ID do paciente.
     * @param medico     O médico que está gerando a receita.
     * @param prescricao A descrição da prescrição.
     * @return Uma String contendo a receita médica formatada, ou uma mensagem de erro se o paciente não for encontrado.
     */
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

    /**
     * Gera um atestado médico formatado para um paciente, indicando dias de afastamento.
     *
     * @param idPaciente      O ID do paciente.
     * @param medico          O médico que está gerando o atestado.
     * @param diasAfastamento O número de dias de afastamento.
     * @return Uma String contendo o atestado médico formatado, ou uma mensagem de erro se o paciente não for encontrado.
     */
    public String gerarAtestado(int idPaciente, Medico medico, int diasAfastamento) {
        Paciente paciente = pacienteServico.buscarPacientePorId(idPaciente);
        if (paciente == null) {
            return "ERRO: Paciente não encontrado";
        }
        return String.format("--- Atestado Médico ---\n" +
                        "Atesto para os devido fins que paciente %s, portador(a) do CPF %s, necessita de %d dias(s) de afastamento.\n\n" +
                        "Data: %s\n" +
                        "Ass: %s (CRM: %s)",
                paciente.getNome(), paciente.getCpf(), diasAfastamento, LocalDate.now(), medico.getNome(), medico.getCrm());
    }

    /**
     * Gera uma declaração de acompanhamento médico.
     *
     * @param idPaciente       O ID do paciente acompanhado.
     * @param medico           O médico que está gerando a declaração.
     * @param diasAfastamento  O número de dias de afastamento.
     * @param nomeAcompanhante O nome da pessoa que acompanhou o paciente.
     * @return Uma String contendo a declaração formatada, ou uma mensagem de erro se o paciente não for encontrado.
     */
    public String gerarDeclaracaoAcompanhamento(int idPaciente, Medico medico, int diasAfastamento, String nomeAcompanhante) {
        Paciente paciente = pacienteServico.buscarPacientePorId(idPaciente);
        if (paciente == null) {
            return "ERRO: Paciente não encontrado";
        }
        return String.format("--- Declaração de Acompanhamento---\n" +
                        "Declaro para os devido fins que o(a) Sr(a) %s, esteve nesta unidade hospitalar no dia %s, acompanhando o(a) paciente %s.\n\n" +
                        "Data: %s\n" +
                        "Ass: %s (CRM: %s)",
                nomeAcompanhante, LocalDate.now(), paciente.getNome(), LocalDate.now(), medico.getNome(), medico.getCrm());
    }

    /**
     * Retorna uma lista de pacientes atendidos por um médico em um mês e ano específicos.
     *
     * @param idMedico       O ID do médico.
     * @param mes            O mês de referência.
     * @param ano            O ano de referência.
     * @param todasConsultas A lista completa de todas as consultas.
     * @return Uma lista de objetos Paciente.
     */
    public List<Paciente> getClientesAtendidosMes(int idMedico, int mes, int ano, List<Consulta> todasConsultas) {
        List<Paciente> pacientesAtendidos = new ArrayList<>();
        for (Consulta consulta : todasConsultas) {
            if (consulta.getDataHora().getYear() == ano) {
                if (consulta.getDataHora().getMonthValue() == mes) {
                    if (consulta.getMedico().getId() == idMedico) {
                        Paciente paciente = consulta.getPaciente();
                        if (!pacientesAtendidos.contains(paciente)) {
                            pacientesAtendidos.add(paciente);
                        }
                    }
                }
            }
        }
        return pacientesAtendidos;
    }

    /**
     * Retorna a contagem total de médicos no banco de dados.
     *
     * @return O número total de médicos.
     */
    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(m) FROM Medico m", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }


}
