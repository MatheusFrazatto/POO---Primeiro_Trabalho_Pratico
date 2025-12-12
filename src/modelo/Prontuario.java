package modelo;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Representa um registro clínico de um atendimento (uma entrada no prontuário).
 * Cada instância desta classe documenta uma consulta específica,
 * detalhando os sintomas relatados, o diagnóstico do médico e a
 * prescrição.
 * <p>
 * Cada Prontuario é associado a um {@link Paciente} e a um {@link Medico} (que o criou).
 */
@Entity
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate data;
    private String sintomas;
    private String diagnostico;
    private String prescricao;

    @ManyToOne
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    public Prontuario() {
    }

    /**
     * Cria um novo registro de prontuário.
     *
     * @param id          O ID deste registro (geralmente 0, para ser definido pelo {@link Paciente}).
     * @param data        A data em que o atendimento foi realizado.
     * @param sintomas    Descrição dos sintomas apresentados pelo paciente.
     * @param diagnostico O diagnóstico firmado pelo médico.
     * @param prescricao  O tratamento, medicamentos ou recomendações prescritas.
     * @param medico      O objeto Medico que realizou este atendimento.
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

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}