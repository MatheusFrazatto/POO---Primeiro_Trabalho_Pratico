package visual;

import main.Main;
import modelo.*;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import java.util.List;

/**
 * Interface gráfica responsável pelas operações do Médico.
 * <p>
 * Permite a visualização e gestão de prontuários médicos, atualização de dados
 * adicionais de saúde dos pacientes, emissão de documentos (atestados, receitas, declarações)
 * e geração de relatórios de atendimentos mensais.
 * </p>
 */
public class TelaMedico extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaMedico.class.getName());
    private int idProntuarioSelecionado = 0;
    /**
     * Creates new form TelaMedico
     */
    public TelaMedico() {
        Main.inicializarDados();
        initComponents();
        preencherComboBoxes();
        
        cbPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carregarDadosDoPaciente();
            }
        });
        
        tabelaProntuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int linha = tabelaProntuario.getSelectedRow();

                if (linha != -1 && cbPaciente.getSelectedItem() instanceof Paciente) {

                    int idProntuario = (int) tabelaProntuario.getValueAt(linha, 0);

                    Paciente p = (Paciente) cbPaciente.getSelectedItem();

                    if (p.getHistoricoProntuarios() != null) {
                        for (Prontuario pront : p.getHistoricoProntuarios()) {
                            if (pront.getId() == idProntuario) {
                                txtSintomas.setText(pront.getSintomas());
                                txtDiagnostico.setText(pront.getDiagnostico());
                                txtPrescricao.setText(pront.getPrescricao());
                                break;
                            }
                        }
                    }
                }
            }
        });
    }
    
    /**
     * Carrega os dados completos do paciente selecionado no ComboBox.
     * <p>
     * Busca o paciente atualizado no banco de dados, preenche os campos da aba de 
     * "Dados Adicionais" (hábitos, doenças, cirurgias), atualiza a tabela de histórico 
     * de prontuários e gera o resumo de saúde no display.
     * </p>
     */
    private void carregarDadosDoPaciente() {
        if (cbPaciente.getSelectedIndex() <= 0) return;
    
        Paciente pSelecionado = (Paciente) cbPaciente.getSelectedItem();

        Paciente pAtualizado = Main.pacienteServico.buscarPacientePorId(pSelecionado.getId());

        DadosAdicionais d = pAtualizado.getDadosAdicionais(); 

        if (d != null) {
            cbFuma.setSelected(d.isFuma());
            cbBebe.setSelected(d.isBebe());
            cbColesterol.setSelected(d.isColesterol());
            cbDiabetico.setSelected(d.isDiabete());
            txtDoencasCardiacas.setText(d.getDoencasCardiacas());
            txtCirurgias.setText(d.getCirurgias());
            txtAlergias.setText(d.getAlergias());
        } else {
            limparAbaDadosAdicionais();
        }

        atualizarTabelaProntuarios(pAtualizado);
        
        if (d != null) {
            StringBuilder resumo = new StringBuilder();

            resumo.append("=== RESUMO DE SAÚDE DO PACIENTE ===\n\n");

            resumo.append("HÁBITOS:\n");
            resumo.append(" • Fuma: ").append(d.isFuma() ? "SIM" : "Não").append("\n");
            resumo.append(" • Bebe: ").append(d.isBebe() ? "SIM" : "Não").append("\n");
            resumo.append(" • Colesterol: ").append(d.isColesterol() ? "ALTO" : "Normal").append("\n");
            resumo.append(" • Diabetes: ").append(d.isDiabete() ? "SIM" : "Não").append("\n\n");

            resumo.append("HISTÓRICO CLÍNICO:\n");

            String cardiacas = (d.getDoencasCardiacas() == null || d.getDoencasCardiacas().isEmpty()) ? "Nenhuma relatada" : d.getDoencasCardiacas();
            resumo.append(" • Doenças Cardíacas: ").append(cardiacas).append("\n");

            String cirurgias = (d.getCirurgias() == null || d.getCirurgias().isEmpty()) ? "Nenhuma relatada" : d.getCirurgias();
            resumo.append(" • Cirurgias Prévias: ").append(cirurgias).append("\n");

            String alergias = (d.getAlergias() == null || d.getAlergias().isEmpty()) ? "Nenhuma conhecida" : d.getAlergias();
            resumo.append(" • Alergias: ").append(alergias).append("\n");

            txtDisplaySaude.setText(resumo.toString());

        } else {
            txtDisplaySaude.setText("Nenhum dado adicional registrado para este paciente.");
        }
    }
    
    /**
     * Atualiza a tabela de prontuários com o histórico do paciente.
     * <p>
     * Limpa a tabela atual e preenche com os prontuários associados ao paciente,
     * exibindo ID, data, nome do médico e diagnóstico.
     * </p>
     *
     * @param p O objeto Paciente cujos prontuários serão listados.
     */
    private void atualizarTabelaProntuarios(Paciente p) {
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tabelaProntuario.getModel();
        modelo.setNumRows(0);
        modelo.setColumnIdentifiers(new Object[]{"ID", "Data", "Médico", "Diagnóstico"});

        if (p.getHistoricoProntuarios() != null) {
            for (Prontuario pront : p.getHistoricoProntuarios()) {
                modelo.addRow(new Object[]{
                    pront.getId(),
                    pront.getData(),
                    pront.getMedico().getNome(),
                    pront.getDiagnostico()
                });
            }
        }
    }
    
    /**
     * Limpa os campos da aba de Dados Adicionais.
     * Reseta checkboxes e campos de texto para o estado inicial.
     */
    private void limparAbaDadosAdicionais() {
        cbFuma.setSelected(false);
        cbBebe.setSelected(false); 
        // ... limpar os outros ...
        txtDoencasCardiacas.setText("");
        txtCirurgias.setText("");
        txtAlergias.setText("");
    }
    
    /**
     * Valida se um Médico e um Paciente foram selecionados nos ComboBoxes.
     * * @return true se ambos estiverem selecionados, false caso contrário.
     */
    private boolean validarSelecao() {
        if (cbMedico.getSelectedIndex() <= 0 || cbPaciente.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Erro: Selecione Médico e Paciente no topo da tela!");
            return false;
        }
        return true;
    }    
    
    /**
     * Preenche os ComboBoxes de Médicos e Pacientes com dados do banco de dados.
     * Utiliza os serviços {@link MedicoServico} e {@link PacienteServico}.
     */
    private void preencherComboBoxes() {
        try {
            DefaultComboBoxModel modelM = (DefaultComboBoxModel) cbMedico.getModel();
            modelM.removeAllElements();
            modelM.addElement("Selecione...");
            
            List<Medico> listaMedicos = Main.medicoServico.listarTodos(); 
            for (Medico m : listaMedicos) {
                modelM.addElement(m);
            }

            DefaultComboBoxModel modelP = (DefaultComboBoxModel) cbPaciente.getModel();
            modelP.removeAllElements();
            modelP.addElement("Selecione...");
            
            List<Paciente> listaPacientes = Main.pacienteServico.getListaPacientes(); 
            for (Paciente p : listaPacientes) {
                modelP.addElement(p);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar dados do banco: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbPaciente = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtSintomas = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDiagnostico = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPrescricao = new javax.swing.JTextField();
        btnSalvarProntuario = new javax.swing.JButton();
        btnExcluirProntuario = new javax.swing.JButton();
        btnLimparProntuario = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaProntuario = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        cbFuma = new javax.swing.JCheckBox();
        cbBebe = new javax.swing.JCheckBox();
        cbColesterol = new javax.swing.JCheckBox();
        cbDiabetico = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtDoencasCardiacas = new javax.swing.JTextField();
        txtCirurgias = new javax.swing.JTextField();
        txtAlergias = new javax.swing.JTextField();
        btnSalvarDadosAdicionais = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDisplaySaude = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtDiasAfastamento = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnGerarAtestado = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMedicamentos = new javax.swing.JTextField();
        btnGerarReceita = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtAcompanhante = new javax.swing.JTextField();
        btnGerarDeclaracao = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtMes = new javax.swing.JTextField();
        txtAno = new javax.swing.JTextField();
        btnRelatorioMensal = new javax.swing.JButton();
        cbMedico = new javax.swing.JComboBox<>();
        btnVoltarM = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Médico Logado:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 9, -1, -1));

        jLabel2.setText("Selecione o Paciente:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, -1, -1));

        getContentPane().add(cbPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(488, 6, 220, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 34, 788, 10));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("Sintomas Apresentados:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 9, -1, -1));
        jPanel1.add(txtSintomas, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 6, 654, -1));

        jLabel4.setText("Diagnóstico:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 34, -1, -1));
        jPanel1.add(txtDiagnostico, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 34, 654, -1));

        jLabel5.setText("Prescrição / Tratamento:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 62, -1, -1));
        jPanel1.add(txtPrescricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 62, 654, -1));

        btnSalvarProntuario.setText("Salvar");
        btnSalvarProntuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarProntuarioActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalvarProntuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 75, -1));

        btnExcluirProntuario.setText("Excluir");
        btnExcluirProntuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirProntuarioActionPerformed(evt);
            }
        });
        jPanel1.add(btnExcluirProntuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, -1, -1));

        btnLimparProntuario.setText("Limpar");
        btnLimparProntuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparProntuarioActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimparProntuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, -1, -1));

        tabelaProntuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tabelaProntuario);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 140, 780, 360));

        jTabbedPane1.addTab("Atendimento / Prontuário", jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbFuma.setText("Fuma");
        jPanel2.add(cbFuma, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 84, -1));

        cbBebe.setText("Bebe");
        jPanel2.add(cbBebe, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 32, 84, -1));

        cbColesterol.setText("Colesterol Alto");
        jPanel2.add(cbColesterol, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 58, -1, -1));

        cbDiabetico.setText("Diabético");
        cbDiabetico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDiabeticoActionPerformed(evt);
            }
        });
        jPanel2.add(cbDiabetico, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 84, 84, -1));

        jLabel6.setText("Doenças Cardiácas:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 113, -1, -1));

        jLabel7.setText("Cirurgias:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 138, -1, -1));

        jLabel8.setText("Alergias:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 166, -1, -1));
        jPanel2.add(txtDoencasCardiacas, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 110, 674, -1));
        jPanel2.add(txtCirurgias, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 138, 674, -1));
        jPanel2.add(txtAlergias, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 166, 674, -1));

        btnSalvarDadosAdicionais.setText("Salvar");
        btnSalvarDadosAdicionais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarDadosAdicionaisActionPerformed(evt);
            }
        });
        jPanel2.add(btnSalvarDadosAdicionais, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 206, 75, -1));

        txtDisplaySaude.setEditable(false);
        txtDisplaySaude.setColumns(20);
        txtDisplaySaude.setRows(5);
        jScrollPane3.setViewportView(txtDisplaySaude);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 790, 230));

        jTabbedPane1.addTab("Dados Adicionais", jPanel2);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setText("ATESTADO");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));
        jPanel3.add(txtDiasAfastamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 28, 299, -1));

        jLabel10.setText("Dias de Afastamento:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 28, -1, -1));

        btnGerarAtestado.setText("Gerar");
        btnGerarAtestado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarAtestadoActionPerformed(evt);
            }
        });
        jPanel3.add(btnGerarAtestado, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 56, 75, -1));
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 85, 400, 10));

        jLabel11.setText("RECEITA");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 101, -1, -1));

        jLabel12.setText("Medicamentos:");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 123, -1, -1));
        jPanel3.add(txtMedicamentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(94, 123, 330, -1));

        btnGerarReceita.setText("Gerar");
        btnGerarReceita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarReceitaActionPerformed(evt);
            }
        });
        jPanel3.add(btnGerarReceita, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 151, 75, -1));
        jPanel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 180, 400, 10));

        jLabel13.setText("DECLARAÇÃO DE ACOMPANHAMENTO");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 196, -1, -1));

        jLabel14.setText("Acompanhante:");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 218, -1, -1));
        jPanel3.add(txtAcompanhante, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 218, 328, -1));

        btnGerarDeclaracao.setText("Gerar");
        btnGerarDeclaracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarDeclaracaoActionPerformed(evt);
            }
        });
        jPanel3.add(btnGerarDeclaracao, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 246, 75, -1));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 275, 800, 231));

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(424, 6, 13, 263));

        jLabel15.setText("RELATÓRIO MENSAL DE ATENDIMENTOS");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 6, -1, -1));

        jLabel16.setText("Mês:");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, -1, -1));

        jLabel17.setText("Ano:");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, -1, -1));

        txtMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMesActionPerformed(evt);
            }
        });
        jPanel3.add(txtMes, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 310, -1));
        jPanel3.add(txtAno, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 310, -1));

        btnRelatorioMensal.setText("Gerar");
        btnRelatorioMensal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatorioMensalActionPerformed(evt);
            }
        });
        jPanel3.add(btnRelatorioMensal, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 120, -1, 20));

        jTabbedPane1.addTab("Emitir Atestados / Receitas", jPanel3);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 50, -1, -1));

        getContentPane().add(cbMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 6, 270, -1));

        btnVoltarM.setText("Voltar");
        btnVoltarM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarMActionPerformed(evt);
            }
        });
        getContentPane().add(btnVoltarM, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Salva um novo prontuário no banco de dados.
     * Coleta os dados de sintomas, diagnóstico e prescrição e chama o serviço para persistência.
     * * @param evt O evento de ação gerado pelo clique no botão Salvar.
     */
    private void btnSalvarProntuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarProntuarioActionPerformed
        if (cbMedico.getSelectedIndex() <= 0 || cbPaciente.getSelectedIndex() <= 0) {
        JOptionPane.showMessageDialog(this, "Erro: Selecione Médico e Paciente no topo da tela!");
        return;
        }

        try {
            Medico m = (Medico) cbMedico.getSelectedItem();
            Paciente p = (Paciente) cbPaciente.getSelectedItem();

            String sintomas = txtSintomas.getText();
            String diagnostico = txtDiagnostico.getText();
            String prescricao = txtPrescricao.getText();

            boolean sucesso = Main.medicoServico.cadastrarProntuario(p.getId(), m, sintomas, diagnostico, prescricao);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Prontuário salvo com sucesso!");

                txtSintomas.setText("");
                txtDiagnostico.setText("");
                txtPrescricao.setText("");
                carregarDadosDoPaciente();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar prontuário. Tente novamente.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro técnico: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSalvarProntuarioActionPerformed

    private void cbDiabeticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDiabeticoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbDiabeticoActionPerformed
    
    /**
     * Salva ou atualiza os dados adicionais de saúde do paciente (hábitos, doenças, etc).
     * * @param evt O evento de ação gerado pelo clique no botão Salvar.
     */
    private void btnSalvarDadosAdicionaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarDadosAdicionaisActionPerformed
        if (cbPaciente.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Selecione um Paciente no topo da tela!");
            return;
        }

        try {
            Paciente p = (Paciente) cbPaciente.getSelectedItem();

            // Pega os dados da tela
            boolean fuma = cbFuma.isSelected();
            boolean bebe = cbBebe.isSelected();
            boolean colesterol = cbColesterol.isSelected();
            boolean diabetico = cbDiabetico.isSelected();

            String doencas = txtDoencasCardiacas.getText();
            String cirurgias = txtCirurgias.getText();
            String alergias = txtAlergias.getText();

            // Salva no banco
            boolean sucesso = Main.medicoServico.atualizarDadosAdicionais(
                p.getId(), fuma, bebe, colesterol, diabetico, doencas, cirurgias, alergias
            );

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Dados de saúde atualizados com sucesso!");

                // --- A MÁGICA ESTÁ AQUI ---
                // Recarrega os dados do banco para atualizar o Display de Resumo automaticamente
                carregarDadosDoPaciente(); 
                // --------------------------

            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar dados.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro técnico: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSalvarDadosAdicionaisActionPerformed
    
    /**
     * Gera um atestado médico formatado na área de texto.
     * * @param evt O evento de ação gerado pelo clique no botão Gerar Atestado.
     */
    private void btnGerarAtestadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarAtestadoActionPerformed
        if (validarSelecao()) {
            try {
                Medico m = (Medico) cbMedico.getSelectedItem();
                Paciente p = (Paciente) cbPaciente.getSelectedItem();
                
                // Pega os dias do campo renomeado
                int dias = Integer.parseInt(txtDiasAfastamento.getText()); 
                
                String texto = Main.medicoServico.gerarAtestado(p.getId(), m, dias);
                jTextArea1.setText(texto);
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Digite apenas números no campo de Dias.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao gerar atestado: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnGerarAtestadoActionPerformed
    
    /**
     * Gera uma receita médica formatada na área de texto.
     * * @param evt O evento de ação gerado pelo clique no botão Gerar Receita.
     */
    private void btnGerarReceitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarReceitaActionPerformed
        if (validarSelecao()) {
            try {
                Medico m = (Medico) cbMedico.getSelectedItem();
                Paciente p = (Paciente) cbPaciente.getSelectedItem();
                
                // Pega o texto do campo renomeado
                String remedios = txtMedicamentos.getText(); 
                
                String texto = Main.medicoServico.gerarReceita(p.getId(), m, remedios);
                jTextArea1.setText(texto);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao gerar receita: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnGerarReceitaActionPerformed
    
    /**
     * Gera uma declaração de acompanhamento formatada na área de texto.
     * * @param evt O evento de ação gerado pelo clique no botão Gerar Declaração.
     */
    private void btnGerarDeclaracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarDeclaracaoActionPerformed
        if (validarSelecao()) {
            try {
                Medico m = (Medico) cbMedico.getSelectedItem();
                Paciente p = (Paciente) cbPaciente.getSelectedItem();
                
                // Pega o nome do acompanhante do campo renomeado
                String acompanhante = txtAcompanhante.getText();
                
                // Passamos 0 dias pois é declaração de comparecimento
                String texto = Main.medicoServico.gerarDeclaracaoAcompanhamento(p.getId(), m, 0, acompanhante);
                jTextArea1.setText(texto);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao gerar declaração: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnGerarDeclaracaoActionPerformed
    /**
     * Exclui o prontuário selecionado na tabela.
     * Solicita confirmação antes de realizar a exclusão.
     * * @param evt O evento de ação gerado pelo clique no botão Excluir.
     */
    private void btnExcluirProntuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirProntuarioActionPerformed
        int linhaSelecionada = tabelaProntuario.getSelectedRow();
    
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um prontuário na tabela para excluir.");
            return;
        }

        int idProntuario = (int) tabelaProntuario.getValueAt(linhaSelecionada, 0);

        if (cbPaciente.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente antes de excluir.");
            return;
        }
        Paciente p = (Paciente) cbPaciente.getSelectedItem();

        int confirmacao = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja excluir o prontuário ID " + idProntuario + "?", 
                "Confirmar Exclusão", 
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                boolean sucesso = Main.medicoServico.removerProntuario(p.getId(), idProntuario);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Prontuário excluído com sucesso.");
                    carregarDadosDoPaciente(); // Atualiza a tabela
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir prontuário.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro técnico: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnExcluirProntuarioActionPerformed
    
    /**
     * Fecha a tela atual e retorna para a Tela Principal.
     * * @param evt O evento de ação gerado pelo clique no botão Voltar.
     */
    private void btnVoltarMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarMActionPerformed
        TelaPrincipal telaPrincipal = new TelaPrincipal();
        telaPrincipal.setVisible(true);
    
        this.dispose();
    }//GEN-LAST:event_btnVoltarMActionPerformed

    private void txtMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMesActionPerformed
    
    /**
     * Fecha a tela atual e retorna para a Tela Principal.
     * * @param evt O evento de ação gerado pelo clique no botão Voltar.
     */
    private void btnRelatorioMensalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioMensalActionPerformed
        if (cbMedico.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Selecione um médico no topo da tela!");
            return;
        }

        try {
            // 2. Pega o Médico, Mês e Ano da tela
            Medico medico = (Medico) cbMedico.getSelectedItem();
            int mes = Integer.parseInt(txtMes.getText());
            int ano = Integer.parseInt(txtAno.getText());

            // 3. Busca TODAS as consultas do sistema (A Secretária que agendou)
            // Precisamos da lista completa para filtrar
            List<Consulta> todasConsultas = Main.consultaServico.getListaConsultas();

            // 4. Chama o serviço do Médico para filtrar só as dele naquele mês
            List<Paciente> atendidos = Main.medicoServico.getClientesAtendidosMes(medico.getId(), mes, ano, todasConsultas);

            // 5. Monta o Texto Bonito
            StringBuilder sb = new StringBuilder();
            sb.append("=== RELATÓRIO MENSAL DE ATENDIMENTOS ===\n");
            sb.append("Médico: ").append(medico.getNome()).append("\n");
            sb.append("Período: ").append(mes).append("/").append(ano).append("\n\n");
            sb.append("LISTA DE PACIENTES:\n");

            if (atendidos.isEmpty()) {
                sb.append("Nenhum paciente atendido neste período.");
            } else {
                for (Paciente p : atendidos) {
                    sb.append(" - ").append(p.getNome());
                    sb.append(" (CPF: ").append(p.getCpf()).append(")\n");
                }
                sb.append("\nTotal: ").append(atendidos.size()).append(" pacientes.");
            }

            // 6. Joga o texto na caixa branca grande (que já existe na tela)
            jTextArea1.setText(sb.toString());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, digite apenas números para Mês e Ano.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnRelatorioMensalActionPerformed

    private void btnLimparProntuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparProntuarioActionPerformed
        txtSintomas.setText("");
        txtDiagnostico.setText("");
        txtPrescricao.setText("");
        tabelaProntuario.clearSelection();
    }//GEN-LAST:event_btnLimparProntuarioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TelaMedico().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcluirProntuario;
    private javax.swing.JButton btnGerarAtestado;
    private javax.swing.JButton btnGerarDeclaracao;
    private javax.swing.JButton btnGerarReceita;
    private javax.swing.JButton btnLimparProntuario;
    private javax.swing.JButton btnRelatorioMensal;
    private javax.swing.JButton btnSalvarDadosAdicionais;
    private javax.swing.JButton btnSalvarProntuario;
    private javax.swing.JButton btnVoltarM;
    private javax.swing.JCheckBox cbBebe;
    private javax.swing.JCheckBox cbColesterol;
    private javax.swing.JCheckBox cbDiabetico;
    private javax.swing.JCheckBox cbFuma;
    private javax.swing.JComboBox<Object> cbMedico;
    private javax.swing.JComboBox<Object> cbPaciente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTable tabelaProntuario;
    private javax.swing.JTextField txtAcompanhante;
    private javax.swing.JTextField txtAlergias;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtCirurgias;
    private javax.swing.JTextField txtDiagnostico;
    private javax.swing.JTextField txtDiasAfastamento;
    private javax.swing.JTextArea txtDisplaySaude;
    private javax.swing.JTextField txtDoencasCardiacas;
    private javax.swing.JTextField txtMedicamentos;
    private javax.swing.JTextField txtMes;
    private javax.swing.JTextField txtPrescricao;
    private javax.swing.JTextField txtSintomas;
    // End of variables declaration//GEN-END:variables
}
