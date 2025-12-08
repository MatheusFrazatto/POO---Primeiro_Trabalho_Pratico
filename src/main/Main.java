package main;

import modelo.*;
import servico.*;
import utilitario.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal do sistema que contém o método main para iniciar a aplicação.
 * Responsável pela inicialização dos serviços, carregamento de dados mock
 * e pela interface de menu interativo para simular os acessos de Secretária e Médico.
 */
public class Main {
    public static PacienteServico pacienteServico = new PacienteServico();
    public static MedicoServico medicoServico = new MedicoServico(pacienteServico);
    public static ConsultaServico consultaServico = new ConsultaServico();
    public static SecretariaServico secretariaServico = new SecretariaServico(pacienteServico, consultaServico);
    public static GerenciadorDeMensagensServico gerenciadorDeMensagensServico = new GerenciadorDeMensagensServico();

    public static List<Medico> medicosDaClinica = new ArrayList<>();


    /**
     * Inicializa dados mock (pacientes, médicos e consultas) para facilitar os testes
     * e o uso da aplicação no ambiente de console.
     */
    private static void inicializarDados() {
        Endereco end1 = new Endereco("Rua A", "100", "", "Centro", "Maringa", "PR");
        Contato cont1 = new Contato("44998765432", "joao.silva@email.com");
        pacienteServico.cadastrarPaciente("João Silva", "12345678900", LocalDate.of(1990, 5, 15), end1, cont1, TipoConvenio.PLANO_SAUDE);

        Endereco end2 = new Endereco("Av. Brasil", "50", "Apto 101", "Zona 7", "Maringa", "PR");
        Contato cont2 = new Contato("44991234567", "");
        pacienteServico.cadastrarPaciente("Maria Oliveira", "09876543211", LocalDate.of(1985, 10, 20), end2, cont2, TipoConvenio.PARTICULAR);

        Medico medico1 = new Medico(1, "Dr. Pedro Santos", "11122233344", 8000.0f, "CRM/PR 12345", "Cardiologia");
        Medico medico2 = new Medico(2, "Dra. Ana Costa", "55566677788", 9500.0f, "CRM/PR 67890", "Dermatologia");

        medicosDaClinica.add(medico1);
        medicosDaClinica.add(medico2);

        consultaServico.cadastrarConsulta(LocalDateTime.now().plusHours(1), medico1, pacienteServico.buscarPacientePorId(1), TipoConsulta.NORMAL);

        LocalDateTime amanha = LocalDate.now().plusDays(1).atStartOfDay();
        consultaServico.cadastrarConsulta(amanha.withHour(9).withMinute(0), medico2, pacienteServico.buscarPacientePorId(2), TipoConsulta.RETORNO);
        consultaServico.cadastrarConsulta(amanha.withHour(10).withMinute(0), medico1, pacienteServico.buscarPacientePorId(1), TipoConsulta.NORMAL);

        System.out.println("--- Dados iniciais (2 Pacientes, 2 Médicos e 3 Consultas) carregados. ---");
    }

    /**
     * O ponto de entrada da aplicação.
     * Inicializa os dados e exibe o menu principal para a interação do usuário.
     *
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        inicializarDados();
        int opcao = -1;

        do {
            System.out.println("\n===== MENU PRINCIPAL - Clínica Saúde & Cia =====");
            System.out.println("1. Acesso Secretária");
            System.out.println("2. Acesso Médico");
            System.out.println("0. Sair");
            System.out.print("Escolha o perfil de acesso: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        menuSecretaria(scanner);
                        break;
                    case 2:
                        menuMedico(scanner);
                        break;
                    case 0:
                        System.out.println("Encerrando o sistema. Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);

        scanner.close();
    }

    /**
     * Exibe o menu de opções para o perfil de Secretária.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void menuSecretaria(Scanner scanner) {
        int opcao = -1;
        do {
            System.out.println("\n----- MENU SECRETÁRIA -----");
            System.out.println("1. Gerenciar Pacientes");
            System.out.println("2. Gerenciar Consultas (Agendar/Cancelar)");
            System.out.println("3. Gerar Relatório de Consultas do Dia Seguinte");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha a opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        menuGerenciarPacientes(scanner);
                        break;
                    case 2:
                        menuGerenciarConsultas(scanner);
                        break;
                    case 3:
                        gerarRelatorioConsultasSecretaria(scanner);
                        break;
                    case 0:
                        System.out.println("Voltando...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }

    /**
     * Exibe o sub-menu de gerenciamento de pacientes (Secretária).
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void menuGerenciarPacientes(Scanner scanner) {
        int opcao = -1;
        do {
            System.out.println("\n----- GERENCIAR PACIENTES -----");
            System.out.println("1. Cadastrar Novo Paciente");
            System.out.println("2. Atualizar Dados do Paciente");
            System.out.println("3. Remover Paciente");
            System.out.println("4. Listar Todos os Pacientes");
            System.out.println("0. Voltar ao Menu Secretária");
            System.out.print("Escolha a opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        cadastrarPaciente(scanner);
                        break;
                    case 2:
                        atualizarPaciente(scanner);
                        break;
                    case 3:
                        removerPaciente(scanner);
                        break;
                    case 4:
                        listarPacientes();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }

    /**
     * Solicita os dados e cadastra um novo paciente através do SecretariaServico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void cadastrarPaciente(Scanner scanner) {
        System.out.println("\n--- CADASTRO DE PACIENTE ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Data de Nascimento (AAAA-MM-DD): ");
        LocalDate dataNascimento = LocalDate.parse(scanner.nextLine());

        System.out.println("-- Endereço --");
        System.out.print("Rua: ");
        String rua = scanner.nextLine();
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        System.out.print("Complemento: ");
        String complemento = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("UF (Ex: PR): ");
        String uf = scanner.nextLine();
        Endereco endereco = new Endereco(rua, numero, complemento, bairro, cidade, uf);

        System.out.println("-- Contato --");
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        Contato contato = new Contato(telefone, email);

        System.out.print("Tipo de Convênio (1 - PARTICULAR, 2 - PLANO DE SAÚDE): ");
        int tipoConvenioOp = scanner.nextInt();
        scanner.nextLine();
        TipoConvenio tipoConvenio = (tipoConvenioOp == 1) ? TipoConvenio.PARTICULAR : TipoConvenio.PLANO_SAUDE;

        Paciente novoPaciente = secretariaServico.cadastrarPaciente(nome, cpf, dataNascimento, endereco, contato, tipoConvenio);
        System.out.println("Paciente cadastrado com sucesso! ID: " + novoPaciente.getId());
    }

    /**
     * Solicita o ID e os novos dados para atualizar um paciente através do SecretariaServico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void atualizarPaciente(Scanner scanner) {
        listarPacientes();
        System.out.print("Digite o ID do paciente a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Paciente paciente = pacienteServico.buscarPacientePorId(id);
        if (paciente == null) {
            System.out.println("Paciente com ID " + id + " não encontrado.");
            return;
        }

        System.out.println("\n--- ATUALIZAR PACIENTE (ID: " + id + ") ---");
        System.out.print("Novo Nome (Atual: " + paciente.getNome() + "): ");
        String nome = scanner.nextLine();

        System.out.println("-- Novo Endereço --");
        System.out.print("Rua (Atual: " + paciente.getEndereco().getRua() + "): ");
        String rua = scanner.nextLine();
        System.out.print("Número (Atual: " + paciente.getEndereco().getNumero() + "): ");
        String numero = scanner.nextLine();
        System.out.print("Complemento (Atual: " + paciente.getEndereco().getComplemento() + "): ");
        String complemento = scanner.nextLine();
        System.out.print("Bairro (Atual: " + paciente.getEndereco().getBairro() + "): ");
        String bairro = scanner.nextLine();
        System.out.print("Cidade (Atual: " + paciente.getEndereco().getCidade() + "): ");
        String cidade = scanner.nextLine();
        System.out.print("UF (Atual: " + paciente.getEndereco().getUf() + "): ");
        String uf = scanner.nextLine();
        Endereco endereco = new Endereco(rua, numero, complemento, bairro, cidade, uf);

        System.out.println("-- Novo Contato --");
        System.out.print("Telefone (Atual: " + paciente.getContato().getTelefone() + "): ");
        String telefone = scanner.nextLine();
        System.out.print("Email (Atual: " + paciente.getContato().getEmail() + "): ");
        String email = scanner.nextLine();
        Contato contato = new Contato(telefone, email);

        System.out.print("Novo Tipo de Convênio (1 - PARTICULAR, 2 - PLANO DE SAÚDE. Atual: " + paciente.getTipoConvenio() + "): ");
        int tipoConvenioOp = scanner.nextInt();
        scanner.nextLine();
        TipoConvenio tipoConvenio = (tipoConvenioOp == 1) ? TipoConvenio.PARTICULAR : TipoConvenio.PLANO_SAUDE;

        if (secretariaServico.atualizarPaciente(id, nome, endereco, contato, tipoConvenio)) {
            System.out.println("Paciente atualizado com sucesso!");
        } else {
            System.out.println("Falha ao atualizar paciente (ID não encontrado, embora já checado).");
        }
    }

    /**
     * Solicita o ID do paciente a ser removido e realiza a remoção através do SecretariaServico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void removerPaciente(Scanner scanner) {
        listarPacientes();
        System.out.print("Digite o ID do paciente a ser removido: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (secretariaServico.removerPaciente(id)) {
            System.out.println("Paciente ID " + id + " removido com sucesso!");
        } else {
            System.out.println("Falha ao remover. Paciente ID " + id + " não encontrado.");
        }
    }

    /**
     * Lista todos os pacientes cadastrados no PacienteServico.
     */
    public static void listarPacientes() {
        System.out.println("\n--- LISTA DE PACIENTES ---");
        if (pacienteServico.getListaPacientes().isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        for (Paciente p : pacienteServico.getListaPacientes()) {
            System.out.printf("ID: %d | Nome: %s | CPF: %s | Convênio: %s | Contato: %s / %s%n",
                    p.getId(), p.getNome(), p.getCpf(), p.getTipoConvenio(), p.getContato().getTelefone(), p.getContato().getEmail());
        }
    }

    /**
     * Exibe o sub-menu de gerenciamento de consultas (Secretária).
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void menuGerenciarConsultas(Scanner scanner) {
        int opcao = -1;
        do {
            System.out.println("\n----- GERENCIAR CONSULTAS -----");
            System.out.println("1. Agendar Nova Consulta");
            System.out.println("2. Cancelar Consulta");
            System.out.println("3. Listar Todas as Consultas");
            System.out.println("0. Voltar ao Menu Secretária");
            System.out.print("Escolha a opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        agendarConsulta(scanner);
                        break;
                    case 2:
                        cancelarConsulta(scanner);
                        break;
                    case 3:
                        listarConsultas();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }

    /**
     * Solicita os dados (Paciente, Médico, Data/Hora) e agenda uma nova consulta
     * através do SecretariaServico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void agendarConsulta(Scanner scanner) {
        System.out.println("\n--- AGENDAR CONSULTA ---");

        listarPacientes();
        System.out.print("ID do Paciente: ");
        int idPaciente = scanner.nextInt();
        scanner.nextLine();
        Paciente paciente = pacienteServico.buscarPacientePorId(idPaciente);
        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        listarMedicos();
        System.out.print("ID do Médico: ");
        int idMedico = scanner.nextInt();
        scanner.nextLine();

        Medico medico = buscarMedicoNaLista(idMedico);
        if (medico == null) {
            System.out.println("Médico não encontrado. Use os IDs da lista.");
            return;
        }

        System.out.print("Data e Hora da Consulta (AAAA-MM-DDTHH:MM - Ex: 2025-12-31T14:30): ");
        LocalDateTime dataHora = LocalDateTime.parse(scanner.nextLine());

        System.out.print("Tipo de Consulta (1 - NORMAL (60 min), 2 - RETORNO (30 min)): ");
        int tipoOp = scanner.nextInt();
        scanner.nextLine();
        TipoConsulta tipoConsulta = (tipoOp == 1) ? TipoConsulta.NORMAL : TipoConsulta.RETORNO;

        Consulta novaConsulta = secretariaServico.cadastrarConsulta(dataHora, medico, paciente, tipoConsulta);

        if (novaConsulta != null) {
            System.out.printf("Consulta agendada! ID: %d | Data: %s | Médico: %s | Tipo: %s%n",
                    novaConsulta.getId(), novaConsulta.getDataHora().toString(), novaConsulta.getMedico().getNome(), novaConsulta.getTipo().toString());
        }
    }

    /**
     * Solicita o ID e remove/cancela uma consulta através do SecretariaServico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void cancelarConsulta(Scanner scanner) {
        listarConsultas();
        System.out.print("Digite o ID da consulta a ser cancelada: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (secretariaServico.removerConsulta(id)) {
            System.out.println("Consulta ID " + id + " cancelada com sucesso!");
        } else {
            System.out.println("Falha ao cancelar. Consulta ID " + id + " não encontrada.");
        }
    }

    /**
     * Lista todas as consultas agendadas no ConsultaServico.
     */
    public static void listarConsultas() {
        System.out.println("\n--- LISTA DE CONSULTAS ---");
        List<Consulta> lista = consultaServico.getListaConsultas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma consulta agendada.");
            return;
        }

        for (Consulta c : lista) {
            System.out.printf("ID: %d | Data/Hora: %s | Paciente: %s (ID: %d) | Médico: %s | Tipo: %s%n",
                    c.getId(), c.getDataHora().toString(), c.getPaciente().getNome(), c.getPaciente().getId(), c.getMedico().getNome(), c.getTipo().toString());
        }
    }

    /**
     * Gera e exibe o relatório de consultas para o dia seguinte, com filtro por tipo de contato,
     * utilizando o SecretariaServico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void gerarRelatorioConsultasSecretaria(Scanner scanner) {
        System.out.println("\n--- RELATÓRIO DE CONSULTAS DO DIA SEGUINTE ---");
        System.out.print("Filtrar por Contato (EMAIL ou TELEFONE): ");
        String filtro = scanner.nextLine().toUpperCase();

        if (!filtro.equals("EMAIL") && !filtro.equals("TELEFONE")) {
            System.out.println("Filtro inválido. Use 'EMAIL' ou 'TELEFONE'.");
            return;
        }

        List<Consulta> consultasFiltradas = secretariaServico.gerarRelatorioConsultas(LocalDate.now().atStartOfDay(), filtro);

        if (consultasFiltradas.isEmpty()) {
            System.out.println("Nenhuma consulta agendada para o dia seguinte com o contato de " + filtro + ".");
            return;
        }

        System.out.println("\n--- CONSULTAS PARA CONFIRMAÇÃO (Filtro por: " + filtro + ") ---");

        for (Consulta c : consultasFiltradas) {
            String contatoInfo = filtro.equals("EMAIL") ? c.getPaciente().getContato().getEmail() : c.getPaciente().getContato().getTelefone();

            System.out.printf("Consulta ID %d | Paciente: %s | Data/Hora: %s | Contato (%s): %s%n",
                    c.getId(), c.getPaciente().getNome(), c.getDataHora().toString(), filtro, contatoInfo);
        }

        System.out.println("    -> SIMULANDO ENVIO de " + filtro + " para confirmação.");

        if (filtro.equals("EMAIL")) {
            gerenciadorDeMensagensServico.enviarEmails(consultasFiltradas);
        } else {
            gerenciadorDeMensagensServico.enviarMensagensSMS(consultasFiltradas);
        }

        System.out.println("Relatório de confirmação e simulação de envio de mensagem concluídos.");
    }

    /**
     * Exibe o menu de opções para o perfil de Médico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void menuMedico(Scanner scanner) {
        int opcao = -1;
        do {
            System.out.println("\n----- MENU MÉDICO -----");
            System.out.println("1. Gerenciar Dados Adicionais do Paciente");
            System.out.println("2. Gerenciar Prontuário");
            System.out.println("3. Gerar Relatórios Médicos (Receita, Atestado, etc.)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha a opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        menuGerenciarDadosAdicionais(scanner);
                        break;
                    case 2:
                        menuGerenciarProntuario(scanner);
                        break;
                    case 3:
                        menuGerarRelatoriosMedicos(scanner);
                        break;
                    case 0:
                        System.out.println("Voltando...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }

    /**
     * Exibe o sub-menu de gerenciamento de dados adicionais (Médico).
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void menuGerenciarDadosAdicionais(Scanner scanner) {
        int opcao = -1;
        do {
            System.out.println("\n----- GERENCIAR DADOS ADICIONAIS -----");
            System.out.println("1. Atualizar Dados Adicionais");
            System.out.println("2. Limpar Dados Adicionais");
            System.out.println("3. Visualizar Dados Adicionais do Paciente");
            System.out.println("0. Voltar ao Menu Médico");
            System.out.print("Escolha a opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        atualizarDadosAdicionais(scanner);
                        break;
                    case 2:
                        limparDadosAdicionais(scanner);
                        break;
                    case 3:
                        visualizarDadosAdicionais(scanner);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }

    /**
     * Solicita os dados de saúde e atualiza os Dados Adicionais do paciente através do MedicoServico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void atualizarDadosAdicionais(Scanner scanner) {
        listarPacientes();
        System.out.print("Digite o ID do paciente: ");
        int idPaciente = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\n--- ATUALIZAR DADOS ADICIONAIS ---");
        System.out.print("Fuma? (true/false): ");
        boolean fuma = scanner.nextBoolean();
        System.out.print("Bebe? (true/false): ");
        boolean bebe = scanner.nextBoolean();
        System.out.print("Colesterol? (true/false): ");
        boolean colesterol = scanner.nextBoolean();
        System.out.print("Diabete? (true/false): ");
        boolean diabetes = scanner.nextBoolean();
        scanner.nextLine();
        System.out.print("Doenças Cardíacas (informar quais, ou Vazio): ");
        String doencasCardiacas = scanner.nextLine();
        System.out.print("Cirurgias (informar quais, ou Vazio): ");
        String cirurgias = scanner.nextLine();
        System.out.print("Alergias (informar quais, ou Vazio): ");
        String alergias = scanner.nextLine();

        if (medicoServico.atualizarDadosAdicionais(idPaciente, fuma, bebe, colesterol, diabetes, doencasCardiacas, cirurgias, alergias)) {
            System.out.println("Dados adicionais do paciente ID " + idPaciente + " atualizados com sucesso!");
        } else {
            System.out.println("Falha ao atualizar. Paciente não encontrado.");
        }
    }

    /**
     * Limpa/reseta os Dados Adicionais de um paciente através do MedicoServico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void limparDadosAdicionais(Scanner scanner) {
        listarPacientes();
        System.out.print("Digite o ID do paciente para limpar dados adicionais: ");
        int idPaciente = scanner.nextInt();
        scanner.nextLine();

        if (medicoServico.limparDadosAdicionais(idPaciente)) {
            System.out.println("Dados adicionais do paciente ID " + idPaciente + " limpos com sucesso!");
        } else {
            System.out.println("Falha ao limpar. Paciente não encontrado.");
        }
    }

    /**
     * Visualiza e exibe os Dados Adicionais de um paciente específico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void visualizarDadosAdicionais(Scanner scanner) {
        listarPacientes();
        System.out.print("Digite o ID do paciente para visualizar dados adicionais: ");
        int idPaciente = scanner.nextInt();
        scanner.nextLine();

        Paciente paciente = pacienteServico.buscarPacientePorId(idPaciente);
        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        DadosAdicionais da = paciente.getDadosAdicionais();
        System.out.println("\n--- DADOS ADICIONAIS DO PACIENTE ID " + idPaciente + " ---");
        System.out.println("Fuma: " + da.isFuma());
        System.out.println("Bebe: " + da.isBebe());
        System.out.println("Colesterol: " + da.isColesterol());
        System.out.println("Diabete: " + da.isDiabete());
        System.out.println("Doenças Cardíacas: " + da.getDoencasCardiacas());
        System.out.println("Cirurgias: " + da.getCirurgias());
        System.out.println("Alergias: " + da.getAlergias());
    }

    /**
     * Exibe o sub-menu de gerenciamento de prontuários (Médico).
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void menuGerenciarProntuario(Scanner scanner) {
        int opcao = -1;
        do {
            System.out.println("\n----- GERENCIAR PRONTUÁRIO -----");
            System.out.println("1. Cadastrar Novo Prontuário");
            System.out.println("2. Atualizar Prontuário Existente");
            System.out.println("3. Remover Prontuário");
            System.out.println("4. Visualizar Histórico de Prontuários");
            System.out.println("0. Voltar ao Menu Médico");
            System.out.print("Escolha a opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        cadastrarProntuario(scanner);
                        break;
                    case 2:
                        atualizarProntuario(scanner);
                        break;
                    case 3:
                        removerProntuario(scanner);
                        break;
                    case 4:
                        visualizarHistoricoProntuarios(scanner);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }

    /**
     * Solicita os dados e cadastra um novo prontuário para o paciente através do MedicoServico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void cadastrarProntuario(Scanner scanner) {
        listarPacientes();
        System.out.print("Digite o ID do paciente: ");
        int idPaciente = scanner.nextInt();
        scanner.nextLine();

        listarMedicos();
        System.out.print("Digite o ID do médico (Use 1 ou 2 para teste): ");
        int idMedico = scanner.nextInt();
        scanner.nextLine();
        Medico medico = buscarMedicoNaLista(idMedico);
        if (medico == null) {
            System.out.println("Médico não encontrado.");
            return;
        }

        System.out.println("\n--- CADASTRAR PRONTUÁRIO ---");
        System.out.print("Sintomas: ");
        String sintomas = scanner.nextLine();
        System.out.print("Diagnóstico: ");
        String diagnostico = scanner.nextLine();
        System.out.print("Prescrição: ");
        String prescricao = scanner.nextLine();

        if (medicoServico.cadastrarProntuario(idPaciente, medico, sintomas, diagnostico, prescricao)) {
            System.out.println("Prontuário cadastrado com sucesso!");
        } else {
            System.out.println("Falha ao cadastrar. Paciente ou Médico não encontrado.");
        }
    }

    /**
     * Solicita o ID do prontuário e os novos dados para atualização através do MedicoServico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void atualizarProntuario(Scanner scanner) {
        listarPacientes();
        System.out.print("Digite o ID do paciente: ");
        int idPaciente = scanner.nextInt();
        scanner.nextLine();

        visualizarHistoricoProntuarios(idPaciente, scanner);

        System.out.print("Digite o ID do prontuário a ser atualizado: ");
        int idProntuario = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\n--- ATUALIZAR PRONTUÁRIO ---");
        System.out.print("Novos Sintomas: ");
        String sintomas = scanner.nextLine();
        System.out.print("Novo Diagnóstico: ");
        String diagnostico = scanner.nextLine();
        System.out.print("Nova Prescrição: ");
        String prescricao = scanner.nextLine();

        if (medicoServico.atualizarProntuario(idPaciente, idProntuario, sintomas, diagnostico, prescricao)) {
            System.out.println("Prontuário ID " + idProntuario + " atualizado com sucesso!");
        } else {
            System.out.println("Falha ao atualizar. Paciente ou Prontuário não encontrado.");
        }
    }

    /**
     * Solicita o ID do prontuário a ser removido e realiza a remoção através do MedicoServico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void removerProntuario(Scanner scanner) {
        listarPacientes();
        System.out.print("Digite o ID do paciente: ");
        int idPaciente = scanner.nextInt();
        scanner.nextLine();

        visualizarHistoricoProntuarios(idPaciente, scanner);

        System.out.print("Digite o ID do prontuário a ser removido: ");
        int idProntuario = scanner.nextInt();
        scanner.nextLine();

        if (medicoServico.removerProntuario(idPaciente, idProntuario)) {
            System.out.println("Prontuário ID " + idProntuario + " removido com sucesso!");
        } else {
            System.out.println("Falha ao remover. Paciente ou Prontuário não encontrado.");
        }
    }

    /**
     * Visualiza e exibe o histórico completo de prontuários de um paciente específico.
     * Sobrecarga interna.
     *
     * @param idPaciente O ID do paciente.
     * @param scanner    Objeto Scanner para leitura da entrada do usuário.
     */
    public static void visualizarHistoricoProntuarios(int idPaciente, Scanner scanner) {
        Paciente paciente = pacienteServico.buscarPacientePorId(idPaciente);
        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        List<Prontuario> historico = paciente.getHistoricoProntuarios();
        if (historico.isEmpty()) {
            System.out.println("Nenhum prontuário encontrado para o paciente " + paciente.getNome() + ".");
            return;
        }

        System.out.println("\n--- HISTÓRICO DE PRONTUÁRIOS - Paciente: " + paciente.getNome() + " ---");
        for (Prontuario p : historico) {
            System.out.printf("ID: %d | Data: %s | Médico: %s (CRM: %s)%n",
                    p.getId(), p.getData(), p.getMedico().getNome(), p.getMedico().getCrm());
            System.out.println("  Sintomas: " + p.getSintomas());
            System.out.println("  Diagnóstico: " + p.getDiagnostico());
            System.out.println("  Prescrição: " + p.getPrescricao());
            System.out.println("--------------------------------------------------");
        }
    }

    /**
     * Visualiza e exibe o histórico completo de prontuários de um paciente (chamado pelo menu).
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void visualizarHistoricoProntuarios(Scanner scanner) {
        listarPacientes();
        System.out.print("Digite o ID do paciente para visualizar prontuários: ");
        int idPaciente = scanner.nextInt();
        scanner.nextLine();
        visualizarHistoricoProntuarios(idPaciente, scanner);
    }


    /**
     * Exibe o sub-menu de geração de relatórios médicos.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void menuGerarRelatoriosMedicos(Scanner scanner) {
        int opcao = -1;
        do {
            System.out.println("\n----- GERAR RELATÓRIOS MÉDICOS -----");
            System.out.println("1. Gerar Receita");
            System.out.println("2. Gerar Atestado");
            System.out.println("3. Gerar Declaração de Acompanhamento");
            System.out.println("4. Gerar Relatorios de Clientes Atendidos no Mês.");
            System.out.println("0. Voltar ao Menu Médico");
            System.out.print("Escolha a opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        gerarReceita(scanner);
                        break;
                    case 2:
                        gerarAtestado(scanner);
                        break;
                    case 3:
                        gerarDeclaracaoAcompanhamento(scanner);
                        break;
                    case 4:
                        gerarRelatorioClientesAtendidos(scanner);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }

    /**
     * Solicita a prescrição e gera uma receita médica formatada através do MedicoServico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void gerarReceita(Scanner scanner) {
        listarPacientes();
        System.out.print("Digite o ID do paciente: ");
        int idPaciente = scanner.nextInt();
        scanner.nextLine();

        listarMedicos();
        System.out.print("Digite o ID do médico (Use 1 ou 2 para teste): ");
        int idMedico = scanner.nextInt();
        scanner.nextLine();
        Medico medico = buscarMedicoNaLista(idMedico);
        if (medico == null) {
            System.out.println("Médico não encontrado.");
            return;
        }

        System.out.print("Prescrição (Medicamentos e dosagem): ");
        String prescricao = scanner.nextLine();

        String receita = medicoServico.gerarReceita(idPaciente, medico, prescricao);
        System.out.println("\n" + receita);
    }

    /**
     * Solicita os dias de afastamento e gera um atestado médico formatado através do MedicoServico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void gerarAtestado(Scanner scanner) {
        listarPacientes();
        System.out.print("Digite o ID do paciente: ");
        int idPaciente = scanner.nextInt();
        scanner.nextLine();

        listarMedicos();
        System.out.print("Digite o ID do médico (Use 1 ou 2 para teste): ");
        int idMedico = scanner.nextInt();
        scanner.nextLine();
        Medico medico = buscarMedicoNaLista(idMedico);
        if (medico == null) {
            System.out.println("Médico não encontrado.");
            return;
        }

        System.out.print("Número de dias de afastamento: ");
        int diasAfastamento = scanner.nextInt();
        scanner.nextLine();

        String atestado = medicoServico.gerarAtestado(idPaciente, medico, diasAfastamento);
        System.out.println("\n" + atestado);
    }

    /**
     * Solicita o nome do acompanhante e gera uma declaração de acompanhamento através do MedicoServico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void gerarDeclaracaoAcompanhamento(Scanner scanner) {
        listarPacientes();
        System.out.print("Digite o ID do paciente: ");
        int idPaciente = scanner.nextInt();
        scanner.nextLine();

        listarMedicos();
        System.out.print("Digite o ID do médico (Use 1 ou 2 para teste): ");
        int idMedico = scanner.nextInt();
        scanner.nextLine();
        Medico medico = buscarMedicoNaLista(idMedico);
        if (medico == null) {
            System.out.println("Médico não encontrado.");
            return;
        }

        System.out.print("Nome do Acompanhante: ");
        String nomeAcompanhante = scanner.nextLine();

        String declaracao = medicoServico.gerarDeclaracaoAcompanhamento(idPaciente, medico, 0, nomeAcompanhante);
        System.out.println("\n" + declaracao);
    }

    /**
     * Gera o relatório de clientes atendidos por um médico em um mês/ano específico.
     *
     * @param scanner Objeto Scanner para leitura da entrada do usuário.
     */
    public static void gerarRelatorioClientesAtendidos(Scanner scanner) {
        System.out.println("\n--- RELATÓRIO DE PACIENTES ATENDIDOS ---");

        listarMedicos();
        System.out.print("Digite o ID do médico: ");
        int idMedico = scanner.nextInt();
        scanner.nextLine();

        Medico medico = buscarMedicoNaLista(idMedico);
        if (medico == null) {
            System.out.println("Médico não encontrado.");
            return;
        }

        System.out.print("Digite o Mês (1-12): ");
        int mes = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o Ano (Ex: 2025): ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        List<Consulta> todasConsultas = consultaServico.getListaConsultas();

        List<Paciente> pacientes = medicoServico.getClientesAtendidosMes(idMedico, mes, ano, todasConsultas);

        if (pacientes.isEmpty()) {
            System.out.printf("Nenhum paciente único atendido por %s no Mês/Ano: %d/%d%n",
                    medico.getNome(), mes, ano);
            return;
        }

        System.out.printf("--- PACIENTES ATENDIDOS POR %s (Mês/Ano: %d/%d) ---%n",
                medico.getNome(), mes, ano);

        for (Paciente p : pacientes) {
            System.out.printf("ID: %d | Nome: %s | CPF: %s%n",
                    p.getId(), p.getNome(), p.getCpf());
        }
    }

    /**
     * Lista os médicos da lista 'medicosDaClinica' (mantida na Main).
     */
    public static void listarMedicos() {
        System.out.println("\n--- LISTA DE MÉDICOS ---");
        if (medicosDaClinica.isEmpty()) {
            System.out.println("Nenhum médico cadastrado na Main.");
            return;
        }
        for (Medico m : medicosDaClinica) {
            System.out.printf("ID: %d | Nome: %s | Especialização: %s%n",
                    m.getId(), m.getNome(), m.getEspecializacao());
        }
    }

    /**
     * Busca um médico por ID na lista 'medicosDaClinica' (mantida na Main).
     *
     * @param id O ID do médico a ser buscado.
     * @return O objeto Medico, ou null se não for encontrado.
     */
    public static Medico buscarMedicoNaLista(int id) {
        for (Medico m : medicosDaClinica) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }
}
