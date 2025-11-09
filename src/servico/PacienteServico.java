package servico;

import modelo.*;
import utilitario.TipoConvenio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável por gerenciar as operações relacionadas a Pacientes.
 * Inclui métodos para cadastrar, atualizar, remover e buscar pacientes.
 */
public class PacienteServico {
    private List<Paciente> listaPacientes = new ArrayList<>();
    private int proximoIdPaciente = 1;

    /**
     * Construtor padrão para o PacienteServico.
     */
    public PacienteServico() {
    }

    /**
     * Cadastra um novo paciente no sistema.
     * Atribui um ID único ao novo paciente.
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
        Paciente novoPaciente = new Paciente(0, nome, cpf, dataNascimento, endereco, contato, tipoConvenio);
        novoPaciente.setId(proximoIdPaciente++);
        this.listaPacientes.add(novoPaciente);
        return novoPaciente;
    }

    /**
     * Atualiza os dados cadastrais de um paciente existente (exceto CPF e Data de Nascimento).
     *
     * @param id           O ID do paciente a ser atualizado.
     * @param nome         O novo nome.
     * @param endereco     O novo endereço.
     * @param contato      As novas informações de contato.
     * @param tipoConvenio O novo tipo de convênio.
     * @return {@code true} se o paciente foi encontrado e atualizado, {@code false} caso contrário.
     */
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

    /**
     * Busca um paciente pelo seu ID.
     *
     * @param id O ID do paciente a ser buscado.
     * @return O objeto Paciente correspondente ao ID, ou {@code null} se não for encontrado.
     */
    public Paciente buscarPacientePorId(int id) {
        for (Paciente paciente : listaPacientes) {
            if (paciente.getId() == id) {
                return paciente;
            }
        }
        return null;
    }

    /**
     * Remove um paciente do sistema com base no seu ID.
     *
     * @param id O ID do paciente a ser removido.
     * @return {@code true} se o paciente foi encontrado e removido, {@code false} caso contrário.
     */
    public boolean removerPaciente(int id) {
        Paciente paciente = buscarPacientePorId(id);
        if (paciente != null) {
            this.listaPacientes.remove(paciente);
            return true;
        }
        return false;
    }

    /**
     * Retorna a lista completa de pacientes cadastrados.
     *
     * @return Uma lista de objetos Paciente.
     */
    public List<Paciente> getListaPacientes() {
        return this.listaPacientes;
    }
}
