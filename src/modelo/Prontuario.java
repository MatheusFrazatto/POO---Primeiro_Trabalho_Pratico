package modelo;

import java.time.LocalDate;

/**
 * Um registro de atendimento do paciente (sintomas, diagnóstico).
 */
public class Prontuario {

    private int id;
    private LocalDate data;
    private String sintomas;
    private String diagnostico;
    private String prescricao;
    private Medico medico;

    /**
     * Cria um novo registro de prontuário.
     *
     * @param id O ID (definido pelo Paciente).
     * @param data A data do atendimento.
     * @param sintomas Os sintomas.
     * @param diagnostico O diagnóstico.
     * @param prescricao A prescrição.
     * @param medico O médico.
     */
    public Prontuario(int id, LocalDate data, String sintomas, String diagnostico, String prescricao, Medico medico) {
        this.id = id;
        this.data = data;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.prescricao = prescricao;
        this.medico = medico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}