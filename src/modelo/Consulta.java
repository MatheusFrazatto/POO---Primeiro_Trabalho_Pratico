package modelo;

import utilitario.TipoConsulta;
import java.time.LocalDateTime;

/**
 * Guarda os dados de uma consulta (médico, paciente, data).
 */
public class Consulta {
    private int id;
    private LocalDateTime dataHora;
    private Medico medico;
    private Paciente paciente;
    private TipoConsulta tipo;

    /**
     * Cria uma nova consulta.
     *
     * @param dataHora Data e hora.
     * @param medico O médico.
     * @param paciente O paciente.
     * @param tipo Tipo (NORMAL ou RETORNO).
     */
    public Consulta(LocalDateTime dataHora, Medico medico, Paciente paciente, TipoConsulta tipo) {
        this.dataHora = dataHora;
        this.medico = medico;
        this.paciente = paciente;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
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

    public TipoConsulta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConsulta tipo) {
        this.tipo = tipo;
    }
}